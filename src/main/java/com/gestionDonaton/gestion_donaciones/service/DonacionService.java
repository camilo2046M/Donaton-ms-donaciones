package com.gestionDonaton.gestion_donaciones.service;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repository;

    public List<Donacion> buscarPorPalabra(String palabra) {
        return repository.findByNombreObjetoContainingIgnoreCase(palabra);
    }

    public boolean actualizarEstadoCompletado(Long id) {
        Optional<Donacion> optional = repository.findById(id);
        if (optional.isPresent()) {
            Donacion donacion = optional.get();
            donacion.setEstado("COMPLETADA");
            repository.save(donacion);
            return true;
        }
        return false;
    }

    public List<Donacion> listarTodas() {
        return repository.findAll();
    }

    public Donacion registrarDonacion(String tipo, Double monto, String nombre, String objeto, String rut, String certificado) {

        return null;
    }
}