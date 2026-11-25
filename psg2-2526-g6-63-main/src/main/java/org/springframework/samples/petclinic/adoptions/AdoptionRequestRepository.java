package org.springframework.samples.petclinic.adoptions;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AdoptionRequestRepository extends CrudRepository<AdoptionRequest, Integer> {

    
    @SuppressWarnings("override")
    List<AdoptionRequest> findAll();

    List<AdoptionRequest> findByStatus(AdoptionRequestStatus status);

    List<AdoptionRequest> findByOriginalOwner_Id(Integer ownerId);

    boolean existsByPet_IdAndStatus(Integer petId, AdoptionRequestStatus status);
}