package com.gestionDonaton.gestion_donaciones.service;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.factory.DonacionFactory;
import com.gestionDonaton.gestion_donaciones.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonacionService {
    @Autowired
    private DonacionRepository repository;

    public Donacion guardarDonacion(String tipo, Double monto, String nombre) {
        Donacion d = DonacionFactory.crearDonacion(tipo);
        d.setMonto(monto);
        d.setDonanteNombre(nombre);
        return repository.save(d);
    }
}