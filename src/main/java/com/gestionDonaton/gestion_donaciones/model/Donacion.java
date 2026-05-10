package com.gestionDonaton.gestion_donaciones.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "donaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_donacion")
@Data
public abstract class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double monto;
    private String donanteNombre;
    private String nombreObjeto;
    private String estado;

}
