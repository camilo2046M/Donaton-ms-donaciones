package com.gestionDonaton.gestion_donaciones.controller;

import com.gestionDonaton.gestion_donaciones.dto.DonacionRequestDTO;
import com.gestionDonaton.gestion_donaciones.dto.DonacionResponseDTO;
import com.gestionDonaton.gestion_donaciones.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService service;

    /**
     * POST /api/v1/donaciones
     * Body JSON: { tipo, monto, nombre, objeto, rut?, certificado? }
     */
    @PostMapping
    public ResponseEntity<DonacionResponseDTO> crearDonacion(
            @RequestBody DonacionRequestDTO request) {
        DonacionResponseDTO response = service.registrarDonacion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/v1/donaciones
     */
    @GetMapping
    public ResponseEntity<List<DonacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    /**
     * GET /api/v1/donaciones/buscar/{palabra}
     */
    @GetMapping("/buscar/{palabra}")
    public ResponseEntity<List<DonacionResponseDTO>> buscarPorPalabra(
            @PathVariable String palabra) {
        return ResponseEntity.ok(service.buscarPorPalabra(palabra));
    }

    /**
     * PATCH /api/v1/donaciones/{id}/completar
     */
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