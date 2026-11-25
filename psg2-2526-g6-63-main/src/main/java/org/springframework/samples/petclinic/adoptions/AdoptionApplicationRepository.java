package org.springframework.samples.petclinic.adoptions;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AdoptionApplicationRepository extends CrudRepository<AdoptionApplication, Integer> {

    List<AdoptionApplication> findAll();

    List<AdoptionApplication> findByAdoptionRequest_Id(Integer adoptionRequestId);

    List<AdoptionApplication> findByApplicant_Id(Integer applicantId);

    List<AdoptionApplication> findByAdoptionRequest_IdAndStatus(Integer adoptionRequestId, AdoptionApplicationStatus status);

    boolean existsByAdoptionRequest_IdAndApplicant_Id(Integer adoptionRequestId, Integer applicantId);
}