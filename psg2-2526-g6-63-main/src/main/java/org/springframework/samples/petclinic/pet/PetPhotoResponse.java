package org.springframework.samples.petclinic.pet;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PetPhotoResponse {

    private Integer id;
    private String url;
    private PetPhotoSource source;
    private LocalDateTime createdAt;
}
