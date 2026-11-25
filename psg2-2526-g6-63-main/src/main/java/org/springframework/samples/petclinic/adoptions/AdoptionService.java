package org.springframework.samples.petclinic.adoptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.AccessDeniedException;
import org.springframework.samples.petclinic.exceptions.LimitReachedException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.exceptions.ResourceNotOwnedException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

    private static final String OWNER_AUTH = "OWNER";

    private final AdoptionRequestRepository adoptionRequestRepository;
    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final PetService petService;
    private final UserService userService;

    public AdoptionService(
            AdoptionRequestRepository adoptionRequestRepository,
            AdoptionApplicationRepository adoptionApplicationRepository,
            PetService petService,
            UserService userService) {
        this.adoptionRequestRepository = adoptionRequestRepository;
        this.adoptionApplicationRepository = adoptionApplicationRepository;
        this.petService = petService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<AdoptionRequest> findAvailableRequests() throws DataAccessException {
        User user = userService.findCurrentUser();

        if (user.hasAuthority(OWNER_AUTH).equals(true)) {
            Owner owner = userService.findOwnerByUser(user.getId());
            return adoptionRequestRepository.findByStatus(AdoptionRequestStatus.OPEN).stream()
                    .filter(r -> !r.getOriginalOwner().getId().equals(owner.getId()))
                    .collect(Collectors.toList());
        }

        return adoptionRequestRepository.findByStatus(AdoptionRequestStatus.OPEN);
    }

    @Transactional(readOnly = true)
    public AdoptionRequest findRequestById(int requestId) throws DataAccessException {
        return adoptionRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("AdoptionRequest", "ID", requestId));
    }

    @Transactional(readOnly = true)
    public AdoptionApplication findApplicationById(int applicationId) throws DataAccessException {
        return adoptionApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("AdoptionApplication", "ID", applicationId));
    }

    @Transactional(readOnly = true)
    public List<AdoptionRequest> findMyRequests() throws DataAccessException {
        Owner owner = getCurrentOwner();
        return adoptionRequestRepository.findByOriginalOwner_Id(owner.getId());
    }

    @Transactional(readOnly = true)
    public List<AdoptionApplication> findMyApplications() throws DataAccessException {
        Owner owner = getCurrentOwner();
        return adoptionApplicationRepository.findByApplicant_Id(owner.getId());
    }

    @Transactional(readOnly = true)
    public List<AdoptionApplication> findApplicationsByRequestId(int requestId) throws DataAccessException {
        AdoptionRequest request = findRequestById(requestId);
        Owner owner = getCurrentOwner();

        if (!request.getOriginalOwner().getId().equals(owner.getId())) {
            throw new ResourceNotOwnedException(request);
        }

        return adoptionApplicationRepository.findByAdoptionRequest_Id(requestId);
    }

    @Transactional(rollbackFor = Exception.class)
    public AdoptionRequest createRequest(Integer petId) throws DataAccessException {
        Owner owner = getCurrentOwner();
        Pet pet = petService.findPetById(petId);

        if (pet.getOwner() == null || !pet.getOwner().getId().equals(owner.getId())) {
            throw new ResourceNotOwnedException(pet);
        }

        if (adoptionRequestRepository.existsByPet_IdAndStatus(petId, AdoptionRequestStatus.OPEN)) {
            throw new InvalidAdoptionOperationException("This pet already has an open adoption request.");
        }

        AdoptionRequest request = new AdoptionRequest();
        request.setPet(pet);
        request.setOriginalOwner(owner);
        request.setStatus(AdoptionRequestStatus.OPEN);
        request.setCreatedAt(LocalDateTime.now());
        request.setClosedAt(null);

        return adoptionRequestRepository.save(request);
    }

    @Transactional(rollbackFor = Exception.class)
    public AdoptionApplication createApplication(Integer requestId, String description)
            throws DataAccessException {
        Owner applicant = getCurrentOwner();
        AdoptionRequest request = findRequestById(requestId);

        if (!request.getStatus().equals(AdoptionRequestStatus.OPEN)) {
            throw new InvalidAdoptionOperationException("This adoption request is not open.");
        }

        if (request.getOriginalOwner().getId().equals(applicant.getId())) {
            throw new AccessDeniedException("You cannot apply for your own adoption request.");
        }

        if (adoptionApplicationRepository.existsByAdoptionRequest_IdAndApplicant_Id(requestId, applicant.getId())) {
            throw new InvalidAdoptionOperationException("You have already applied to this adoption request.");
        }

        AdoptionApplication application = new AdoptionApplication();
        application.setAdoptionRequest(request);
        application.setApplicant(applicant);
        application.setDescription(description);
        application.setStatus(AdoptionApplicationStatus.PENDING);
        application.setCreatedAt(LocalDateTime.now());

        return adoptionApplicationRepository.save(application);
    }

    @Transactional(rollbackFor = Exception.class)
    public AdoptionApplication approveApplication(Integer applicationId)
            throws DataAccessException, DuplicatedPetNameException {
        Owner currentOwner = getCurrentOwner();
        AdoptionApplication application = findApplicationById(applicationId);
        AdoptionRequest request = application.getAdoptionRequest();

        if (!request.getOriginalOwner().getId().equals(currentOwner.getId())) {
            throw new ResourceNotOwnedException(request);
        }

        if (!request.getStatus().equals(AdoptionRequestStatus.OPEN)) {
            throw new InvalidAdoptionOperationException("This adoption request is not open.");
        }

        if (!application.getStatus().equals(AdoptionApplicationStatus.PENDING)) {
            throw new InvalidAdoptionOperationException("Only pending applications can be approved.");
        }

        Owner applicant = application.getApplicant();

        if (!petService.underLimit(applicant)) {
            throw new LimitReachedException("Pets", applicant.getClinic().getPlan());
        }

        Pet pet = request.getPet();
        pet.setOwner(applicant);
        petService.savePet(pet);

        application.setStatus(AdoptionApplicationStatus.APPROVED);
        adoptionApplicationRepository.save(application);

        List<AdoptionApplication> pendingApplications = adoptionApplicationRepository
                .findByAdoptionRequest_IdAndStatus(request.getId(), AdoptionApplicationStatus.PENDING);

        for (AdoptionApplication pendingApplication : pendingApplications) {
            if (!pendingApplication.getId().equals(application.getId())) {
                pendingApplication.setStatus(AdoptionApplicationStatus.REJECTED);
                adoptionApplicationRepository.save(pendingApplication);
            }
        }

        request.setStatus(AdoptionRequestStatus.APPROVED);
        request.setClosedAt(LocalDateTime.now());
        adoptionRequestRepository.save(request);

        return application;
    }

    @Transactional(rollbackFor = Exception.class)
    public AdoptionApplication rejectApplication(Integer applicationId) throws DataAccessException {
        Owner currentOwner = getCurrentOwner();
        AdoptionApplication application = findApplicationById(applicationId);
        AdoptionRequest request = application.getAdoptionRequest();

        if (!request.getOriginalOwner().getId().equals(currentOwner.getId())) {
            throw new ResourceNotOwnedException(request);
        }

        if (!request.getStatus().equals(AdoptionRequestStatus.OPEN)) {
            throw new InvalidAdoptionOperationException("This adoption request is not open.");
        }

        if (!application.getStatus().equals(AdoptionApplicationStatus.PENDING)) {
            throw new InvalidAdoptionOperationException("Only pending applications can be rejected.");
        }

        application.setStatus(AdoptionApplicationStatus.REJECTED);
        return adoptionApplicationRepository.save(application);
    }

    @Transactional(rollbackFor = Exception.class)
    public AdoptionRequest closeRequest(Integer requestId) throws DataAccessException {
        Owner currentOwner = getCurrentOwner();
        AdoptionRequest request = findRequestById(requestId);

        if (!request.getOriginalOwner().getId().equals(currentOwner.getId())) {
            throw new ResourceNotOwnedException(request);
        }

        if (!request.getStatus().equals(AdoptionRequestStatus.OPEN)) {
            throw new InvalidAdoptionOperationException("Only open adoption requests can be closed.");
        }

        List<AdoptionApplication> pendingApplications = adoptionApplicationRepository
                .findByAdoptionRequest_IdAndStatus(requestId, AdoptionApplicationStatus.PENDING);

        for (AdoptionApplication application : pendingApplications) {
            application.setStatus(AdoptionApplicationStatus.REJECTED);
            adoptionApplicationRepository.save(application);
        }

        request.setStatus(AdoptionRequestStatus.CLOSED);
        request.setClosedAt(LocalDateTime.now());

        return adoptionRequestRepository.save(request);
    }

    private Owner getCurrentOwner() {
        User user = userService.findCurrentUser();

        if (!user.hasAuthority(OWNER_AUTH).equals(true)) {
            throw new AccessDeniedException("Only owners can use adoption features.");
        }

        return userService.findOwnerByUser(user.getId());
    }
}