package org.springframework.samples.petclinic.pethotelroom;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PetHotelRoomRepository extends CrudRepository<PetHotelRoom, Integer> {
    
    List<PetHotelRoom> findAll();

    @Query("SELECT r FROM PetHotelRoom r WHERE r.clinic.clinicOwner.user.id = :userId")
    List<PetHotelRoom> findRoomsByUserId(int userId);
}