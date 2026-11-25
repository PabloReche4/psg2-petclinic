package org.springframework.samples.petclinic.community;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPhotoRequest {

    @NotBlank
    private String imageUrl;

    private String caption;

    private Integer petId;
}
