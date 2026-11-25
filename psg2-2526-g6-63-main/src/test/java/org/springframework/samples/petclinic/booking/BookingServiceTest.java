package org.springframework.samples.petclinic.booking;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.samples.petclinic.pethotelroom.PetHotelRoom;
 
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
 
    @Mock
    private BookingRepository bookingRepository;
 
    @InjectMocks
    private BookingService bookingService;
  
    // Estos métodos auxiliares crean objetos de prueba con los atributos mínimos necesarios.

    private PetType buildPetType(int id, String name) {
        PetType pt = new PetType();
        pt.setId(id);
        pt.setName(name);
        return pt;
    }
 
    private Pet buildPet(int id, PetType type) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setType(type);
        return pet;
    }
 
    private PetHotelRoom buildRoom(int id, PetType allowedType) {
        PetHotelRoom room = new PetHotelRoom();
        room.setId(id);
        room.setAllowedPetType(allowedType);
        return room;
    }
 
    private Booking buildValidBooking(Integer id, LocalDate start, LocalDate end,
                                      Pet pet, PetHotelRoom room) {
        Booking b = new Booking();
        b.setId(id);
        b.setStartDate(start);
        b.setEndDate(end);
        b.setPet(pet);
        b.setRoom(room);
        return b;
    }
  
    private PetType catType;
    private PetType dogType;
    private Pet cat;
    private PetHotelRoom catRoom;
 
    @BeforeEach
    void setUp() {
        catType  = buildPetType(1, "cat");
        dogType  = buildPetType(2, "dog");
        cat      = buildPet(10, catType);
        catRoom  = buildRoom(20, catType);
    }
 
  
    @Test
    void findAllReturnsAllBookings() {
        // Prueba que findAll devuelve todas las reservas cuando hay al menos una.

        Booking b = buildValidBooking(1,
                LocalDate.now(), LocalDate.now().plusDays(1), cat, catRoom);
        when(bookingRepository.findAll()).thenReturn(List.of(b));

        List<Booking> result = bookingService.findAll();

        assertEquals(1, result.size());
        verify(bookingRepository).findAll();
    }

    @Test
    void findAllEmptyList() {
        // Prueba que findAll devuelve una lista vacía cuando no hay reservas.

        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());

        List<Booking> result = bookingService.findAll();

        assertTrue(result.isEmpty());
    }
 
    @Test
    void findByIdFound() {
        // Prueba que findById devuelve la reserva cuando existe.
        Booking b = buildValidBooking(1,
                LocalDate.now(), LocalDate.now().plusDays(1), cat, catRoom);
        when(bookingRepository.findById(1)).thenReturn(Optional.of(b));

        Booking result = bookingService.findById(1);

        assertEquals(b, result);
    }

    @Test
    void findByIdNotFoundThrowsIllegalArgumentException() {
        // Prueba que findById lanza IllegalArgumentException cuando la reserva no existe.
        when(bookingRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.findById(999));
    }
    

    // ══════════════════════════════════════════════════════════════════════════
    // SAVE
    // ══════════════════════════════════════════════════════════════════════════
 
    @Test
    void saveNullStartDateThrowsIllegalArgumentException() {
        // Prueba que save lanza excepción si startDate es null.
        Booking b = buildValidBooking(1, null, LocalDate.now().plusDays(1), cat, catRoom);

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
    }

    @Test
    void saveNullEndDateThrowsIllegalArgumentException() {
        // Prueba que save lanza excepción si endDate es null.
        Booking b = buildValidBooking(1, LocalDate.now(), null, cat, catRoom);

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
    }

    @Test
    void saveBothDatesNullThrowsIllegalArgumentException() {
        // Prueba que save lanza excepción si ambas fechas son null.
        Booking b = buildValidBooking(1, null, null, cat, catRoom);

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
    }
 
    @Test
    void saveStartDateAfterEndDateThrowsIllegalArgumentException() {
        // Prueba que save lanza excepción si startDate es después de endDate.
        LocalDate start = LocalDate.now().plusDays(5);
        LocalDate end   = LocalDate.now();
        Booking b = buildValidBooking(1, start, end, cat, catRoom);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
        assertTrue(ex.getMessage().contains("Start date must be before end date"));
    }

    @Test
    void savePetTypeMatchesDoesNotThrowTypeError() {
        // Prueba que si el tipo de mascota coincide con el de la habitación, no lanza error de tipo.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(2);
        Booking b = buildValidBooking(1, start, end, cat, catRoom);
        
        when(bookingRepository.save(b)).thenReturn(b);
 
        assertDoesNotThrow(() -> bookingService.save(b));
    }

    @Test
    void savePetTypeMismatchThrowsIllegalArgumentException() {
        // Prueba que save lanza excepción si el tipo de mascota no coincide con el de la habitación.
        // Usa una habitación para perros con una mascota gato, y verifica el mensaje de error.
        PetHotelRoom dogRoom = buildRoom(30, dogType);
        Booking b = buildValidBooking(1,
                LocalDate.now(), LocalDate.now().plusDays(2), cat, dogRoom);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
        assertTrue(ex.getMessage().contains("not allowed in this room"));
    }

 
    @Test
    void saveRoomAlreadyBookedThrowsIllegalArgumentException() {
        // Prueba que save lanza excepción si la habitación ya está reservada en las fechas.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(2);
        Booking b = buildValidBooking(null, start, end, cat, catRoom);
 
        Booking conflicting = buildValidBooking(99, start, end, cat, catRoom);
        when(bookingRepository.findoverlappingBookingsForRoom(
                catRoom.getId(), start, end)).thenReturn(new ArrayList<>(List.of(conflicting)));
 
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
        assertTrue(ex.getMessage().contains("room is already booked"));
    }
 
    @Test
    void saveRoomOverlapIsCurrentBookingShouldNotThrow() {
        // Prueba que al actualizar una reserva, la solapamiento consigo misma se ignora.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(3);
        Booking b = buildValidBooking(5, start, end, cat, catRoom);
 
        Booking self = buildValidBooking(5, start, end, cat, catRoom);
        when(bookingRepository.findoverlappingBookingsForRoom(
                catRoom.getId(), start, end)).thenReturn(new ArrayList<>(List.of(self)));
        when(bookingRepository.findoverlappingBookingsForPet(
                cat.getId(), start, end)).thenReturn(new ArrayList<>(List.of(self)));
        when(bookingRepository.save(b)).thenReturn(b);
 
        assertDoesNotThrow(() -> bookingService.save(b));
    }
 
    @Test
    void saveRoomOverlapContainsBothSelfAndOtherThrowsIllegalArgumentException() {
        // Prueba que si hay solapamiento con otra reserva además de sí misma, lanza error.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(2);
        Booking b = buildValidBooking(5, start, end, cat, catRoom);
 
        Booking self  = buildValidBooking(5,  start, end, cat, catRoom);
        Booking other = buildValidBooking(99, start, end, cat, catRoom);
        when(bookingRepository.findoverlappingBookingsForRoom(
                catRoom.getId(), start, end)).thenReturn(new ArrayList<>(List.of(self, other)));
 
        assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
    }
 
    @Test
    void savePetAlreadyBookedThrowsIllegalArgumentException() {
        // Prueba que save lanza excepción si la mascota ya ha reservado una habitación en las mismas fechas.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(2);
        Booking b = buildValidBooking(null, start, end, cat, catRoom);
 
        PetHotelRoom anotherRoom = buildRoom(21, catType);
        Booking conflicting = buildValidBooking(98, start, end, cat, anotherRoom);
        when(bookingRepository.findoverlappingBookingsForPet(
                cat.getId(), start, end)).thenReturn(new ArrayList<>(List.of(conflicting)));
 
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
        assertTrue(ex.getMessage().contains("pet is already booked"));
    }
 
    @Test
    void savePetOverlapIsCurrentBookingShouldNotThrow() {
        // Prueba que al actualizar, el solapamiento de la mascota consigo misma se ignora.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(3);
        Booking b = buildValidBooking(7, start, end, cat, catRoom);
 
        when(bookingRepository.findoverlappingBookingsForRoom(
                catRoom.getId(), start, end)).thenReturn(Collections.emptyList());
        Booking self = buildValidBooking(7, start, end, cat, catRoom);
        when(bookingRepository.findoverlappingBookingsForPet(
                cat.getId(), start, end)).thenReturn(new ArrayList<>(List.of(self)));
        when(bookingRepository.save(b)).thenReturn(b);
 
        assertDoesNotThrow(() -> bookingService.save(b));
    }
 
    @Test
    void savePetOverlapContainsBothSelfAndOtherThrowsIllegalArgumentException() {
        // Prueba que si la reserva que quiero hacer tiene solapamiento con otra reserva además de sí misma, lanza error.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(2);
        Booking b = buildValidBooking(7, start, end, cat, catRoom);
 
        Booking self  = buildValidBooking(7,  start, end, cat, catRoom);
        Booking other = buildValidBooking(97, start, end, cat, catRoom);
        when(bookingRepository.findoverlappingBookingsForPet(
                cat.getId(), start, end)).thenReturn(new ArrayList<>(List.of(self, other)));
 
        assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
    }
 

    @Test
    void saveNewBookingSuccess() {
        // Prueba el camino correcto para crear una nueva reserva.
        // Todas las validaciones pasan, se guarda exitosamente.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(4);
        Booking b = buildValidBooking(null, start, end, cat, catRoom);

        when(bookingRepository.findoverlappingBookingsForRoom(
                catRoom.getId(), start, end)).thenReturn(Collections.emptyList());
        when(bookingRepository.findoverlappingBookingsForPet(
                cat.getId(), start, end)).thenReturn(Collections.emptyList());
        when(bookingRepository.save(b)).thenReturn(b);

        Booking saved = bookingService.save(b);

        assertNotNull(saved);
        verify(bookingRepository).save(b);
    }

    @Test
    void saveUpdateExistingBookingSuccess() {
        // Prueba el camino correcto para actualizar una reserva existente.
        LocalDate start = LocalDate.now();
        LocalDate end   = start.plusDays(3);
        Booking b = buildValidBooking(3, start, end, cat, catRoom);
        Booking self = buildValidBooking(3, start, end, cat, catRoom);
        
        when(bookingRepository.findoverlappingBookingsForRoom(
                catRoom.getId(), start, end)).thenReturn(new ArrayList<>(List.of(self)));
        when(bookingRepository.findoverlappingBookingsForPet(
                cat.getId(), start, end)).thenReturn(new ArrayList<>(List.of(self)));
        when(bookingRepository.save(b)).thenReturn(b);
 
        Booking saved = bookingService.save(b);
 
        assertEquals(3, saved.getId());
        verify(bookingRepository).save(b);
    }

    @Test
    void saveSameStartEndDateThrowsIllegalArgumentException() {
        LocalDate same = LocalDate.now().plusDays(1);
        Booking b = buildValidBooking(null, same, same, cat, catRoom);

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.save(b));
    }

    // ══════════════════════════════════════════════════════════════════════════
    // delete
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    void deleteExistingBookingSuccess() {
        // Prueba que delete elimina una reserva existente.
        Booking b = buildValidBooking(1,
                LocalDate.now(), LocalDate.now().plusDays(1), cat, catRoom);
        when(bookingRepository.findById(1)).thenReturn(Optional.of(b));

        bookingService.delete(1);

        verify(bookingRepository).delete(b);
    }

    @Test
    void deleteNonExistingBookingThrowsIllegalArgumentException() {
        // Prueba que delete lanza excepción si la reserva no existe.
        // findById devuelve empty, así que error.
        when(bookingRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.delete(999));
        verify(bookingRepository, never()).delete(any());
    }
}