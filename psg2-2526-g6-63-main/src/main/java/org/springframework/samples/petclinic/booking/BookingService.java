package org.springframework.samples.petclinic.booking;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        return (List<Booking>) bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Booking findById(int id) {
        return bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    @Transactional
    public Booking save(Booking booking) {
        if (booking.getStartDate() == null || booking.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }

        if (!booking.getStartDate().isBefore(booking.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Integer petTypeId = booking.getPet().getType().getId();
        Integer roomAllowedTypeId = booking.getRoom().getAllowedPetType().getId();

        if (!petTypeId.equals(roomAllowedTypeId)) {
            throw new IllegalArgumentException("The pet type (" + booking.getPet().getType().getName() +
                    ") is not allowed in this room (allows " + booking.getRoom().getAllowedPetType().getName() + ")");
        }
        List<Booking> overlappingBookingsForRoom = bookingRepository.findoverlappingBookingsForRoom(
                booking.getRoom().getId(), booking.getStartDate(), booking.getEndDate());
        overlappingBookingsForRoom.removeIf(b -> b.getId().equals(booking.getId()));
        if (!overlappingBookingsForRoom.isEmpty()) {
            throw new IllegalArgumentException("The room is already booked for the selected dates");
        }

        List<Booking> overlappingBookingsForPet = bookingRepository
                .findoverlappingBookingsForPet(booking.getPet().getId(), booking.getStartDate(), booking.getEndDate());
        overlappingBookingsForPet.removeIf(b -> b.getId().equals(booking.getId()));
        if (!overlappingBookingsForPet.isEmpty()) {
            throw new IllegalArgumentException("The pet is already booked for the selected dates");
        }
        return bookingRepository.save(booking);
    }

    @Transactional
    public void delete(Integer id) {
        Booking existingBooking = findById(id);
        bookingRepository.delete(existingBooking);
    }
}
