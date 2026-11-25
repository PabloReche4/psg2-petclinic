package org.springframework.samples.petclinic.adoptions;

import java.time.LocalDateTime;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adoption_applications")
public class AdoptionApplication extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "adoption_request_id", referencedColumnName = "id")
    @NotNull
    private AdoptionRequest adoptionRequest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    @NotNull
    private Owner applicant;

    @Column(name = "description", nullable = false, length = 1000)
    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull
    private AdoptionApplicationStatus status;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;
}