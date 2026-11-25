package org.springframework.samples.petclinic.community;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.samples.petclinic.exceptions.AccessDeniedException;
import org.springframework.samples.petclinic.exceptions.DuplicateVoteException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.FileStorageService;
import org.springframework.samples.petclinic.util.ImageValidationService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommunityPhotoService {

    private static final String OWNER_AUTH = "OWNER";
    private static final String ADMIN_AUTH = "ADMIN";

    private final CommunityPhotoRepository communityPhotoRepository;
    private final PhotoVoteRepository photoVoteRepository;
    private final PetService petService;
    private final UserService userService;
    private final ImageValidationService imageValidationService;
    private final FileStorageService fileStorageService;

    public CommunityPhotoService(
        CommunityPhotoRepository communityPhotoRepository,
        PhotoVoteRepository photoVoteRepository,
        PetService petService,
        UserService userService,
        ImageValidationService imageValidationService,
        FileStorageService fileStorageService
    ) {
        this.communityPhotoRepository = communityPhotoRepository;
        this.photoVoteRepository = photoVoteRepository;
        this.petService = petService;
        this.userService = userService;
        this.imageValidationService = imageValidationService;
        this.fileStorageService = fileStorageService;
    }

    @Transactional(readOnly = true)
    public List<CommunityPhotoResponse> findApproved(int limit) {
        User user = userService.findCurrentUser();
        return communityPhotoRepository
            .findByStatusOrderByCreatedAtDesc(CommunityPhotoStatus.APPROVED, PageRequest.of(0, limit))
            .stream()
            .map(photo -> toResponse(photo, user))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommunityPhotoResponse> findPending(int limit) {
        User user = userService.findCurrentUser();
        if (!user.hasAuthority(ADMIN_AUTH)) {
            throw new AccessDeniedException();
        }
        return communityPhotoRepository
            .findByStatusOrderByCreatedAtDesc(CommunityPhotoStatus.PENDING, PageRequest.of(0, limit))
            .stream()
            .map(photo -> toResponse(photo, user))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommunityPhotoResponse> findMine() {
        User user = userService.findCurrentUser();
        Owner owner = userService.findOwnerByUser(user.getId());
        return communityPhotoRepository.findByOwnerIdOrderByCreatedAtDesc(owner.getId())
            .stream()
            .map(photo -> toResponse(photo, user))
            .collect(Collectors.toList());
    }

    @Transactional
    public CommunityPhotoResponse createPhoto(CommunityPhotoRequest request) {
        User user = userService.findCurrentUser();
        if (!user.hasAuthority(OWNER_AUTH)) {
            throw new AccessDeniedException();
        }

        Owner owner = userService.findOwnerByUser(user.getId());
        imageValidationService.validateImageUrl(request.getImageUrl());

        CommunityPhoto photo = new CommunityPhoto();
        photo.setUrl(request.getImageUrl());
        photo.setCaption(request.getCaption());
        photo.setCreatedAt(LocalDateTime.now());
        photo.setStatus(CommunityPhotoStatus.PENDING);
        photo.setOwner(owner);

        if (request.getPetId() != null) {
            Pet pet = petService.findPetById(request.getPetId());
            if (pet.getOwner() == null || !pet.getOwner().getId().equals(owner.getId())) {
                throw new AccessDeniedException("Pet not owned by current user.");
            }
            photo.setPet(pet);
        }

        CommunityPhoto saved = communityPhotoRepository.save(photo);
        return toResponse(saved, user);
    }

    @Transactional
    public CommunityPhotoResponse createPhotoFromUpload(MultipartFile file, String caption, Integer petId) {
        User user = userService.findCurrentUser();
        if (!user.hasAuthority(OWNER_AUTH)) {
            throw new AccessDeniedException();
        }

        Owner owner = userService.findOwnerByUser(user.getId());
        imageValidationService.validateImageFile(file);
        String url = fileStorageService.store(file);

        CommunityPhoto photo = new CommunityPhoto();
        photo.setUrl(url);
        photo.setCaption(caption);
        photo.setCreatedAt(LocalDateTime.now());
        photo.setStatus(CommunityPhotoStatus.PENDING);
        photo.setOwner(owner);

        if (petId != null) {
            Pet pet = petService.findPetById(petId);
            if (pet.getOwner() == null || !pet.getOwner().getId().equals(owner.getId())) {
                throw new AccessDeniedException("Pet not owned by current user.");
            }
            photo.setPet(pet);
        }

        CommunityPhoto saved = communityPhotoRepository.save(photo);
        return toResponse(saved, user);
    }

    @Transactional
    public CommunityPhotoResponse updatePhoto(int photoId, CommunityPhotoRequest request) {
        User user = userService.findCurrentUser();
        CommunityPhoto photo = RestPreconditions.checkNotNull(
            communityPhotoRepository.findById(photoId).orElse(null),
            "CommunityPhoto",
            "ID",
            photoId
        );
        validateOwnerOrAdmin(photo, user);

        imageValidationService.validateImageUrl(request.getImageUrl());
        photo.setUrl(request.getImageUrl());
        photo.setCaption(request.getCaption());
        if (!user.hasAuthority(ADMIN_AUTH)) {
            photo.setStatus(CommunityPhotoStatus.PENDING);
        }
        return toResponse(photo, user);
    }

    @Transactional
    public void deletePhoto(int photoId) {
        User user = userService.findCurrentUser();
        CommunityPhoto photo = RestPreconditions.checkNotNull(
            communityPhotoRepository.findById(photoId).orElse(null),
            "CommunityPhoto",
            "ID",
            photoId
        );
        validateOwnerOrAdmin(photo, user);
        fileStorageService.deleteByUrl(photo.getUrl());
        communityPhotoRepository.delete(photo);
    }

    @Transactional
    public CommunityPhotoResponse updateStatus(int photoId, CommunityPhotoStatus status) {
        User user = userService.findCurrentUser();
        if (!user.hasAuthority(ADMIN_AUTH)) {
            throw new AccessDeniedException();
        }
        CommunityPhoto photo = RestPreconditions.checkNotNull(
            communityPhotoRepository.findById(photoId).orElse(null),
            "CommunityPhoto",
            "ID",
            photoId
        );
        photo.setStatus(status);
        return toResponse(photo, user);
    }

    @Transactional
    public void votePhoto(int photoId) {
        User user = userService.findCurrentUser();
        CommunityPhoto photo = RestPreconditions.checkNotNull(
            communityPhotoRepository.findById(photoId).orElse(null),
            "CommunityPhoto",
            "ID",
            photoId
        );

        if (photoVoteRepository.existsByPhotoIdAndUserId(photoId, user.getId())) {
            throw new DuplicateVoteException("You have already voted for this photo.");
        }

        PhotoVote vote = new PhotoVote();
        vote.setPhoto(photo);
        vote.setUser(user);
        vote.setCreatedAt(LocalDateTime.now());
        photoVoteRepository.save(vote);
    }

    @Transactional(readOnly = true)
    public CommunityPhotoResponse findVotes(int photoId) {
        User user = userService.findCurrentUser();
        CommunityPhoto photo = RestPreconditions.checkNotNull(
            communityPhotoRepository.findById(photoId).orElse(null),
            "CommunityPhoto",
            "ID",
            photoId
        );
        return toResponse(photo, user);
    }

    @Transactional(readOnly = true)
    public List<CommunityPhotoResponse> findTopMonthly(YearMonth month, int limit) {
        User user = userService.findCurrentUser();
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.atEndOfMonth().atTime(23, 59, 59);
        return communityPhotoRepository
            .findTopByMonth(CommunityPhotoStatus.APPROVED, start, end, PageRequest.of(0, limit))
            .stream()
            .map(result -> {
                CommunityPhoto photo = (CommunityPhoto) result[0];
                return toResponse(photo, user);
            })
            .collect(Collectors.toList());
    }

    private void validateOwnerOrAdmin(CommunityPhoto photo, User user) {
        if (user.hasAuthority(ADMIN_AUTH)) {
            return;
        }
        if (!user.hasAuthority(OWNER_AUTH)) {
            throw new AccessDeniedException();
        }
        Owner owner = userService.findOwnerByUser(user.getId());
        if (!photo.getOwner().getId().equals(owner.getId())) {
            throw new AccessDeniedException("Photo not owned by current user.");
        }
    }

    private CommunityPhotoResponse toResponse(CommunityPhoto photo, User user) {
        long votes = photoVoteRepository.countByPhotoId(photo.getId());
        boolean hasVoted = photoVoteRepository.existsByPhotoIdAndUserId(photo.getId(), user.getId());
        return CommunityPhotoResponse.builder()
            .id(photo.getId())
            .imageUrl(photo.getUrl())
            .caption(photo.getCaption())
            .petId(photo.getPet() != null ? photo.getPet().getId() : null)
            .petName(photo.getPet() != null ? photo.getPet().getName() : null)
            .ownerId(photo.getOwner().getId())
            .ownerName(photo.getOwner().getFirstName() + " " + photo.getOwner().getLastName())
            .status(photo.getStatus())
            .createdAt(photo.getCreatedAt())
            .votes(votes)
            .hasVoted(hasVoted)
            .build();
    }
}
