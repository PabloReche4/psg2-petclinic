package org.springframework.samples.petclinic.photo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/photos")
@Tag(name = "Photos", description = "Photo suggestions API")
@SecurityRequirement(name = "bearerAuth")
public class CatPhotoSuggestionController {

    private final CatApiService catApiService;
    private final PetService petService;

    public CatPhotoSuggestionController(CatApiService catApiService, PetService petService) {
        this.catApiService = catApiService;
        this.petService = petService;
    }

    @GetMapping("suggestions")
    public ResponseEntity<List<CatImageResponse>> getSuggestions(
        @RequestParam("petType") String petType,
        @RequestParam(value = "limit", required = false, defaultValue = "6") int limit
    ) {
        if ("cat".equalsIgnoreCase(petType)) {
            return new ResponseEntity<>(catApiService.fetchSuggestions(limit), HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }

    @GetMapping("/pets/{petId}/suggestions")
    public ResponseEntity<List<CatImageResponse>> getSuggestionsForPet(
        @PathVariable("petId") int petId,
        @RequestParam(value = "limit", required = false, defaultValue = "6") int limit
    ) {
        Pet pet = RestPreconditions.checkNotNull(petService.findPetById(petId), "Pet", "ID", petId);
        String typeName = pet.getType() != null ? pet.getType().getName() : "";
        if ("cat".equalsIgnoreCase(typeName)) {
            return new ResponseEntity<>(catApiService.fetchSuggestions(limit), HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }
}
