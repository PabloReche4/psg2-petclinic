package org.springframework.samples.petclinic.adoptions;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAdoptionApplicationDTO {

    @NotBlank
    private String description;
}