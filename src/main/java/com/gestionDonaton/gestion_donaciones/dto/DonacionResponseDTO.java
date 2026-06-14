package com.gestionDonaton.gestion_donaciones.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO de respuesta — lo que el frontend recibe y el BFF reenvía.
 * Mapea los campos de la entidad Donacion a un JSON limpio.
 */
@Data
@Builder
public class DonacionResponseDTO {
    private Long id;
    private String tipo;          // "INDIVIDUAL" o "EMPRESARIAL"
    private Double monto;
    private String donanteNombre;
    private String nombreObjeto;
    private String estado;        // "PENDIENTE" o "COMPLETADA"
    private String rutEmpresa;    // null si es INDIVIDUAL
    private String certificadoImpuestos; // null si es INDIVIDUAL
}