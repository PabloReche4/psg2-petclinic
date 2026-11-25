package org.springframework.samples.petclinic.pet;

import java.time.LocalDateTime;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pet_photos")
@Getter
@Setter
public class PetPhoto extends BaseEntity {

    @Column(name = "url", length = 2048, nullable = false)
    private String url;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", length = 20, nullable = false)
    private PetPhotoSource source;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
}
