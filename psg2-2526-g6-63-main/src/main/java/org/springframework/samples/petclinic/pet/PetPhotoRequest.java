package org.springframework.samples.petclinic.pet;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetPhotoRequest {

    @NotBlank
    private String url;

    private PetPhotoSource source;

    private Boolean setAsPrimary;
}
