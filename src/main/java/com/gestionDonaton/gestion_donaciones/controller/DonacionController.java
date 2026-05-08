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
            @RequestParam(required = false) String rut,
            @RequestParam(required = false) String certificado) {

        Donacion nuevaDonacion = service.registrarDonacion(tipo, monto, nombre, rut, certificado);
        return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Donacion>> obtenerTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/salud")
    public String healthCheck() {
        return "Servicio de Gestión de Donaciones operando en puerto 8081";
    }
}