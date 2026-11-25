package org.springframework.samples.petclinic.adoptions;

import java.time.LocalDateTime;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adoption_requests")
public class AdoptionRequest extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    @NotNull
    private Pet pet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "original_owner_id", referencedColumnName = "id")
    @NotNull
    private Owner originalOwner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull
    private AdoptionRequestStatus status;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}