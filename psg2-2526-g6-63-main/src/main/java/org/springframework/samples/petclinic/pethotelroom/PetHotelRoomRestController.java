package org.springframework.samples.petclinic.pethotelroom;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pethotelrooms")
@Tag(name = "Pet Hotel Rooms", description = "The Pet Hotel Rooms management API")
@SecurityRequirement(name = "bearerAuth")
public class PetHotelRoomRestController {

    private final PetHotelRoomService roomService;

    @Autowired
    public PetHotelRoomRestController(PetHotelRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<PetHotelRoom>> findAllRooms(@RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return new ResponseEntity<>(roomService.findRoomsByUserId(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(roomService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "{roomId}")
    public ResponseEntity<PetHotelRoom> findRoomById(@PathVariable("roomId") int roomId) {
        return new ResponseEntity<>(roomService.findRoomById(roomId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PetHotelRoom> createRoom(@RequestBody @Valid PetHotelRoom room) {
        PetHotelRoom newRoom = new PetHotelRoom();
        BeanUtils.copyProperties(room, newRoom, "id");
        return new ResponseEntity<>(roomService.save(newRoom), HttpStatus.CREATED);
    }

    @PutMapping(value = "{roomId}")
    public ResponseEntity<PetHotelRoom> updateRoom(@PathVariable("roomId") int roomId,
            @RequestBody @Valid PetHotelRoom room) {
        RestPreconditions.checkNotNull(roomService.findRoomById(roomId), "PetHotelRoom", "ID", roomId);
        return new ResponseEntity<>(roomService.update(room, roomId), HttpStatus.OK);
    }

    @DeleteMapping(value = "{roomId}")
    public ResponseEntity<MessageResponse> deleteRoom(@PathVariable("roomId") int roomId) {
        RestPreconditions.checkNotNull(roomService.findRoomById(roomId), "PetHotelRoom", "ID", roomId);
        roomService.delete(roomId);
        return new ResponseEntity<>(new MessageResponse("Room deleted!"), HttpStatus.OK);
    }
}