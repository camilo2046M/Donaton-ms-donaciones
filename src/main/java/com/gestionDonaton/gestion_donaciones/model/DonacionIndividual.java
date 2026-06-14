
package com.gestionDonaton.gestion_donaciones.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("INDIVIDUAL")
@Getter
@Setter
@ToString(callSuper = true)
public class DonacionIndividual extends Donacion {

    public DonacionIndividual() {
        super();
    }
}