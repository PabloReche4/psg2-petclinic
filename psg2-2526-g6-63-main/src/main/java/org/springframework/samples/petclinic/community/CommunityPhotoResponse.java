package org.springframework.samples.petclinic.community;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityPhotoResponse {

    private Integer id;
    private String imageUrl;
    private String caption;
    private Integer petId;
    private String petName;
    private Integer ownerId;
    private String ownerName;
    private CommunityPhotoStatus status;
    private LocalDateTime createdAt;
    private long votes;
    private boolean hasVoted;
}
