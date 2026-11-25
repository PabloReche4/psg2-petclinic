package org.springframework.samples.petclinic.pet;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pets")
@Tag(name = "Pet Photos", description = "Pet photo management API")
@SecurityRequirement(name = "bearerAuth")
public class PetPhotoRestController {

    private final PetPhotoService petPhotoService;

    public PetPhotoRestController(PetPhotoService petPhotoService) {
        this.petPhotoService = petPhotoService;
    }

    @GetMapping("/{petId}/photos")
    public ResponseEntity<List<PetPhotoResponse>> getPhotos(@PathVariable("petId") int petId) {
        return new ResponseEntity<>(petPhotoService.findByPetId(petId), HttpStatus.OK);
    }

    @PostMapping("/{petId}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PetPhotoResponse> addPhoto(
        @PathVariable("petId") int petId,
        @RequestBody @Valid PetPhotoRequest request
    ) {
        PetPhotoResponse response = petPhotoService.addPetPhoto(petId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/{petId}/photo/upload", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PetPhotoResponse> uploadPhoto(
        @PathVariable("petId") int petId,
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "setAsPrimary", required = false, defaultValue = "true") boolean setAsPrimary
    ) {
        PetPhotoResponse response = petPhotoService.addPetPhotoFile(petId, file, setAsPrimary);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
