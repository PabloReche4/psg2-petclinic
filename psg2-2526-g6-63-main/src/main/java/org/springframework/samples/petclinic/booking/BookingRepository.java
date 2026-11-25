package org.springframework.samples.petclinic.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND b.startDate <= :endDate AND b.endDate >= :startDate")
    List<Booking> findoverlappingBookingsForRoom(@Param("roomId") int roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM Booking b WHERE b.pet.id = :petId AND b.startDate <= :endDate AND b.endDate >= :startDate")
    List<Booking> findoverlappingBookingsForPet(@Param("petId") int petId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
}
