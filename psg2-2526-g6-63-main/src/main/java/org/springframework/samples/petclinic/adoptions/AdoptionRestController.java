package org.springframework.samples.petclinic.adoptions;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/adoptions")
@Tag(name = "Adoptions", description = "The Adoptions management API")
@SecurityRequirement(name = "bearerAuth")
public class AdoptionRestController {

    private final AdoptionService adoptionService;

    public AdoptionRestController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @GetMapping
    public ResponseEntity<List<AdoptionRequest>> findAvailableRequests() {
        return new ResponseEntity<>(adoptionService.findAvailableRequests(), HttpStatus.OK);
    }

    @GetMapping("{requestId}")
    public ResponseEntity<AdoptionRequest> findRequestById(@PathVariable("requestId") int requestId) {
        AdoptionRequest request = RestPreconditions.checkNotNull(
                adoptionService.findRequestById(requestId),
                "AdoptionRequest",
                "ID",
                requestId);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("my-requests")
    public ResponseEntity<List<AdoptionRequest>> findMyRequests() {
        return new ResponseEntity<>(adoptionService.findMyRequests(), HttpStatus.OK);
    }

    @GetMapping("my-applications")
    public ResponseEntity<List<AdoptionApplication>> findMyApplications() {
        return new ResponseEntity<>(adoptionService.findMyApplications(), HttpStatus.OK);
    }

    @GetMapping("{requestId}/applications")
    public ResponseEntity<List<AdoptionApplication>> findApplicationsByRequestId(
            @PathVariable("requestId") int requestId) {
        return new ResponseEntity<>(adoptionService.findApplicationsByRequestId(requestId), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdoptionRequest> createRequest(@RequestBody @Valid CreateAdoptionRequestDTO dto)
            throws DataAccessException {
        AdoptionRequest created = adoptionService.createRequest(dto.getPetId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("{requestId}/applications")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdoptionApplication> createApplication(
            @PathVariable("requestId") int requestId,
            @RequestBody @Valid CreateAdoptionApplicationDTO dto) throws DataAccessException {
        AdoptionApplication created = adoptionService.createApplication(requestId, dto.getDescription());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("applications/{applicationId}/approve")
    public ResponseEntity<AdoptionApplication> approveApplication(@PathVariable("applicationId") int applicationId)
            throws DataAccessException, DuplicatedPetNameException {
        AdoptionApplication approved = adoptionService.approveApplication(applicationId);
        return new ResponseEntity<>(approved, HttpStatus.OK);
    }

    @PutMapping("applications/{applicationId}/reject")
    public ResponseEntity<AdoptionApplication> rejectApplication(@PathVariable("applicationId") int applicationId)
            throws DataAccessException {
        AdoptionApplication rejected = adoptionService.rejectApplication(applicationId);
        return new ResponseEntity<>(rejected, HttpStatus.OK);
    }

    @PutMapping("{requestId}/close")
    public ResponseEntity<AdoptionRequest> closeRequest(@PathVariable("requestId") int requestId)
            throws DataAccessException {
        AdoptionRequest closed = adoptionService.closeRequest(requestId);
        return new ResponseEntity<>(closed, HttpStatus.OK);
    }

    @GetMapping("health")
    public ResponseEntity<MessageResponse> health() {
        return new ResponseEntity<>(new MessageResponse("Adoptions endpoint working!"), HttpStatus.OK);
    }
}