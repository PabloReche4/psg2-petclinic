package org.springframework.samples.petclinic.pethotelroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.PetType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pet_hotel_rooms")
public class PetHotelRoom extends BaseEntity {

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "size")
    @NotNull
    @Min(1)
    private Integer size;

    @ManyToOne
    @JoinColumn(name = "allowed_pet_type_id", referencedColumnName = "id")
    @NotNull
    private PetType allowedPetType;

    @ManyToOne
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    @NotNull
    private Clinic clinic;
}