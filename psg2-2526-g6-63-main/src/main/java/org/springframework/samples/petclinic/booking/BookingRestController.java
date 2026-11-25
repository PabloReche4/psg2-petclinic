package org.springframework.samples.petclinic.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/bookings")
@SecurityRequirement(name = "bearerAuth")
public class BookingRestController {

    private final BookingService bookingService;
    private final UserService userService;
    private final OwnerService ownerService;
    private final PetService petService;
    private static final String ADMIN_AUTH = "ADMIN";
    private static final String OWNER_AUTH = "OWNER";

    @Autowired
    public BookingRestController(BookingService bookingService, UserService userService, OwnerService ownerService,
            PetService petService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        User currentUser = userService.findCurrentUser();
        if (currentUser.hasAuthority(ADMIN_AUTH)) {
            return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
        } else if (currentUser.hasAuthority(OWNER_AUTH)) {
            Owner owner = ownerService.optFindOwnerByUser(currentUser.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found for current user"));
            List<Booking> bookings = bookingService.findAll();
            List<Pet> ownerPets = petService.findAllPetsByOwnerId(owner.getId());
            List<Booking> ownerBookings = bookings.stream()
                    .filter(booking -> ownerPets.contains(booking.getPet()))
                    .toList();
            return new ResponseEntity<>(ownerBookings, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Integer id) {
        Booking booking = bookingService.findById(id);
        verifyUserBooking(booking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        User currentUser = userService.findCurrentUser();
        if (!currentUser.hasAuthority(OWNER_AUTH)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Owner owner = ownerService.optFindOwnerByUser(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found for current user"));
        List<Pet> ownerPets = petService.findAllPetsByOwnerId(owner.getId());
        Boolean isPetOwner = ownerPets.stream()
                .anyMatch(pet -> pet.getId().equals(booking.getPet().getId()));

        if (!isPetOwner) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Booking savedBooking = bookingService.save(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") Integer id, @Valid @RequestBody Booking booking) {
        Booking existingBooking = bookingService.findById(id);
        verifyUserBooking(existingBooking);
        booking.setId(id);
        Booking updatedBooking = bookingService.save(booking);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Integer id) {
        Booking existingBooking = bookingService.findById(id);
        verifyUserBooking(existingBooking);
        bookingService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void verifyUserBooking(Booking booking) {
        User currentUser = userService.findCurrentUser();
        if (currentUser.hasAnyAuthority(ADMIN_AUTH)) {
            return;
        } else if (currentUser.hasAnyAuthority(OWNER_AUTH)) {
            Owner owner = ownerService.optFindOwnerByUser(currentUser.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found for current user"));
            List<Pet> ownerPets = petService.findAllPetsByOwnerId(owner.getId());
            Boolean isPetOwner = ownerPets.stream()
                .anyMatch(pet -> pet.getId().equals(booking.getPet().getId()));
            if (!isPetOwner) {
                throw new RuntimeException("The pet does not belong to the current user");
            }
        } else {
            throw new RuntimeException("The current user does not have sufficient permissions");
        }
    }

    @ExceptionHandler({ ConcurrentBookingException.class })
    public ResponseEntity<MessageResponse> handleConcurrentBookingException(ConcurrentBookingException e) {
        return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<MessageResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
