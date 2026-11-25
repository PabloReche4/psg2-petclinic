package org.springframework.samples.petclinic.pet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.exceptions.AccessDeniedException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.FileStorageService;
import org.springframework.samples.petclinic.util.ImageValidationService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetPhotoService {

    private static final String OWNER_AUTH = "OWNER";
    private static final String ADMIN_AUTH = "ADMIN";
    private static final String VET_AUTH = "VET";
    private static final String CLINIC_OWNER_AUTH = "CLINIC_OWNER";

    private final PetService petService;
    private final PetPhotoRepository petPhotoRepository;
    private final UserService userService;
    private final ImageValidationService imageValidationService;
    private final FileStorageService fileStorageService;

    public PetPhotoService(
        PetService petService,
        PetPhotoRepository petPhotoRepository,
        UserService userService,
        ImageValidationService imageValidationService,
        FileStorageService fileStorageService
    ) {
        this.petService = petService;
        this.petPhotoRepository = petPhotoRepository;
        this.userService = userService;
        this.imageValidationService = imageValidationService;
        this.fileStorageService = fileStorageService;
    }

    @Transactional(readOnly = true)
    public List<PetPhotoResponse> findByPetId(int petId) {
        Pet pet = petService.findPetById(petId);
        User user = userService.findCurrentUser();
        validateOwnership(pet, user);
        return petPhotoRepository.findByPetId(petId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public PetPhotoResponse addPetPhoto(int petId, PetPhotoRequest request) {
        Pet pet = petService.findPetById(petId);
        User user = userService.findCurrentUser();
        validateOwnership(pet, user);

        imageValidationService.validateImageUrl(request.getUrl());

        PetPhoto photo = new PetPhoto();
        photo.setPet(pet);
        photo.setUrl(request.getUrl());
        photo.setCreatedAt(LocalDateTime.now());
        photo.setUploadedBy(user);
        photo.setSource(request.getSource() != null ? request.getSource() : PetPhotoSource.UPLOAD);
        PetPhoto saved = petPhotoRepository.save(photo);

        Boolean setAsPrimary = request.getSetAsPrimary();
        if (setAsPrimary == null || setAsPrimary.booleanValue()) {
            pet.setPhotoUrl(request.getUrl());
        }

        return toResponse(saved);
    }

    @Transactional
    public PetPhotoResponse addPetPhotoFile(int petId, MultipartFile file, boolean setAsPrimary) {
        Pet pet = petService.findPetById(petId);
        User user = userService.findCurrentUser();
        validateOwnership(pet, user);

        imageValidationService.validateImageFile(file);
        String url = fileStorageService.store(file);

        PetPhoto photo = new PetPhoto();
        photo.setPet(pet);
        photo.setUrl(url);
        photo.setCreatedAt(LocalDateTime.now());
        photo.setUploadedBy(user);
        photo.setSource(PetPhotoSource.UPLOAD);
        PetPhoto saved = petPhotoRepository.save(photo);

        if (setAsPrimary) {
            fileStorageService.deleteByUrl(pet.getPhotoUrl());
            pet.setPhotoUrl(url);
        }

        return toResponse(saved);
    }

    private void validateOwnership(Pet pet, User user) {
        if (user.hasAuthority(OWNER_AUTH)) {
            Owner owner = userService.findOwnerByUser(user.getId());
            if (pet.getOwner() == null || !owner.getId().equals(pet.getOwner().getId())) {
                throw new AccessDeniedException("Pet not owned by current user.");
            }
            return;
        }

        if (!user.hasAnyAuthority(ADMIN_AUTH, VET_AUTH, CLINIC_OWNER_AUTH)) {
            throw new AccessDeniedException();
        }
    }

    private PetPhotoResponse toResponse(PetPhoto photo) {
        return PetPhotoResponse.builder()
            .id(photo.getId())
            .url(photo.getUrl())
            .source(photo.getSource())
            .createdAt(photo.getCreatedAt())
            .build();
    }
}
