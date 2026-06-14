package com.gestionDonaton.gestion_donaciones.controller;

import com.gestionDonaton.gestion_donaciones.dto.DonacionRequestDTO;
import com.gestionDonaton.gestion_donaciones.dto.DonacionResponseDTO;
import com.gestionDonaton.gestion_donaciones.service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/donaciones")
@Tag(name = "Gestión de Donaciones", description = "Endpoints para el registro, actualización y consulta de donaciones")
@RequestMapping("/api/v1/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService service;


    @PostMapping("/crear")
    @Operation(
            summary = "Registrar una nueva donación",
            description = "Crea un registro de donación en el sistema. Soporta tanto donaciones monetarias como de objetos físicos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Donación registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos")
    })
    public ResponseEntity<Donacion> crearDonacion(
            @Parameter(description = "Tipo de donación (ej. Moneda, Alimento, Ropa)", required = true)
            @RequestParam String tipo,

            @Parameter(description = "Monto de la donación (0 si es un objeto físico)", required = true)
            @RequestParam Double monto,

            @Parameter(description = "Nombre del donante", required = true)
            @RequestParam String nombre,

            @Parameter(description = "Descripción del objeto donado (en caso de aplicar)", required = true)
            @RequestParam String objeto,

            @Parameter(description = "RUT del donante (opcional para extranjeros o anónimos)", required = false)
            @RequestParam(required = false) String rut,

            @Parameter(description = "Indica si requiere certificado de donación (opcional)", required = false)
            @RequestParam(required = false) String certificado) {

        Donacion nuevaDonacion = service.registrarDonacion(tipo, monto, nombre, objeto, rut, certificado);
        return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/completar")
    @Operation(
            summary = "Marcar donación como completada",
            description = "Cambia el estado de una donación específica a 'COMPLETADA' utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna donación con el ID proporcionado")
    })
    public ResponseEntity<String> marcarComoCompletada(
            @Parameter(description = "ID único de la donación a actualizar", required = true)
            @PathVariable Long id) {
        boolean actualizado = service.actualizarEstadoCompletado(id);

        if (actualizado) {
            return ResponseEntity.ok("Donación " + id + " COMPLETADA");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID no encontrado");
        }

   
    @PostMapping
    public ResponseEntity<DonacionResponseDTO> crearDonacion(
            @RequestBody DonacionRequestDTO request) {
        DonacionResponseDTO response = service.registrarDonacion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

  
    @GetMapping
    public ResponseEntity<List<DonacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());

    }

 
    @GetMapping("/buscar/{palabra}")
    public ResponseEntity<List<DonacionResponseDTO>> buscarPorPalabra(
            @PathVariable String palabra) {
        return ResponseEntity.ok(service.buscarPorPalabra(palabra));
    }

   
    @PatchMapping("/{id}/completar")
    public ResponseEntity<?> completar(@PathVariable Long id) {
        DonacionResponseDTO result = service.actualizarEstadoCompletado(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Donación con ID " + id + " no encontrada.");
    }
}