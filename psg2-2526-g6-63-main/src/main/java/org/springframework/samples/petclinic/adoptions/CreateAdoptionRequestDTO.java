package org.springframework.samples.petclinic.adoptions;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAdoptionRequestDTO {

    @NotNull
    private Integer petId;
}