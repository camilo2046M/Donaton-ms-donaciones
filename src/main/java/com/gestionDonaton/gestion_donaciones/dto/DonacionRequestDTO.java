package com.gestionDonaton.gestion_donaciones.dto;

import lombok.Data;

/**
 * DTO para recibir el body del POST /api/donaciones/crear
 * El frontend envía este JSON desde el formulario de donación.
 */
@Data
public class DonacionRequestDTO {
    private String tipo;            // "INDIVIDUAL" o "EMPRESARIAL"
    private Double monto;
    private String nombre;          // nombre del donante
    private String objeto;          // nombre del objeto/recurso donado
    private String rut;             // solo para EMPRESARIAL (opcional)
    private String certificado;     // solo para EMPRESARIAL (opcional)
}