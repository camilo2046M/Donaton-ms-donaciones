
package com.gestionDonaton.gestion_donaciones.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("INDIVIDUAL") // Valor que se guardará en la columna tipo_donacion
@Data
@EqualsAndHashCode(callSuper = true)
public class DonacionIndividual extends Donacion {

    // Esta clase hereda id, monto y donanteNombre de la clase base Donacion.

    public DonacionIndividual() {
        super();
    }
}