package com.gestionDonaton.gestion_donaciones.service;

import com.gestionDonaton.gestion_donaciones.factory.DonacionFactory;
import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial;
import com.gestionDonaton.gestion_donaciones.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repository;

    public Donacion registrarDonacion(String tipo, Double monto, String nombre, String objeto, String rut, String certificado) {

        Donacion donacion = DonacionFactory.crearDonacion(tipo);
        donacion.setMonto(monto);
        donacion.setDonanteNombre(nombre);
        donacion.setNombreObjeto(objeto);


        if (donacion instanceof DonacionEmpresarial && rut != null) {
            ((DonacionEmpresarial) donacion).setRutEmpresa(rut);
            ((DonacionEmpresarial) donacion).setCertificadoImpuestos(certificado);
        }

        return repository.save(donacion);
    }

    public List<Donacion> listarTodas() {
        return repository.findAll();
    }
}