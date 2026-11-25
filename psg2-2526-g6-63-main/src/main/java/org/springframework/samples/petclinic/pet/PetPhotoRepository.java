package org.springframework.samples.petclinic.pet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetPhotoRepository extends JpaRepository<PetPhoto, Integer> {

    List<PetPhoto> findByPetId(Integer petId);
}
