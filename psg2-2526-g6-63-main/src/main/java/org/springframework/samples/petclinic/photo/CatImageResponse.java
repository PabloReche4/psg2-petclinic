package org.springframework.samples.petclinic.photo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatImageResponse {

    private String id;
    private String url;
    private Integer width;
    private Integer height;
}
