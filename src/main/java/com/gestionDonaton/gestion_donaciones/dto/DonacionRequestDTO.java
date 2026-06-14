package com.gestionDonaton.gestion_donaciones.dto;

import lombok.Data;


@Data
public class DonacionRequestDTO {
    private String tipo;            // "INDIVIDUAL" o "EMPRESARIAL"
    private Double monto;
    private String nombre;          // nombre del donante
    private String objeto;          // nombre del objeto/recurso donado
    private String rut;             // solo para EMPRESARIAL (opcional)
    private String certificado;     // solo para EMPRESARIAL (opcional)
}