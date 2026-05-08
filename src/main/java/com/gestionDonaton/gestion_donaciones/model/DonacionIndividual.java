
package com.gestionDonaton.gestion_donaciones.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("INDIVIDUAL")
@Data
@EqualsAndHashCode(callSuper = true)
public class DonacionIndividual extends Donacion {



    public DonacionIndividual() {
        super();
    }
}