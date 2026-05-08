package com.gestionDonaton.gestion_donaciones.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("EMPRESARIAL")
@Data
@EqualsAndHashCode(callSuper = true)
public class DonacionEmpresarial extends Donacion {
    private String rutEmpresa;
    private String certificadoImpuestos;
}