package com.gestionDonaton.gestion_donaciones.controller;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService service;


    @PostMapping("/crear")
    public ResponseEntity<Donacion> crearDonacion(
            @RequestParam String tipo,
            @RequestParam Double monto,
            @RequestParam String nombre,
            @RequestParam String objeto,
            @RequestParam(required = false) String rut,
            @RequestParam(required = false) String certificado) {

        Donacion nuevaDonacion = service.registrarDonacion(tipo, monto, nombre, objeto, rut, certificado);
        return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<String> marcarComoCompletada(@PathVariable Long id) {
        // Llamamos al servicio para que haga el trabajo sucio
        boolean actualizado = service.actualizarEstadoCompletado(id);

        if (actualizado) {
            System.out.println("LOG: Donación " + id + " marcada como COMPLETADA exitosamente.");
            return ResponseEntity.ok("Estado de donación " + id + " actualizado a COMPLETADA");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la donación con ID: " + id);
        }
    }

    @GetMapping("/buscar/{palabra}")
    public List<Donacion> buscarPorPalabra(@PathVariable String palabra) {
        return service.buscarPorPalabra(palabra);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Donacion>> obtenerTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
}