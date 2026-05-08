package com.gestionDonaton.gestion_donaciones.service;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionIndividual;
import com.gestionDonaton.gestion_donaciones.repository.DonacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DonacionServiceTest {

    @Mock
    private DonacionRepository repository;

    @InjectMocks
    private DonacionService service;

    @Test
    void cuandoRegistrarDonacionIndividual_entoncesRetornaDonacion() {
        String tipo = "INDIVIDUAL";
        Double monto = 100.0;
        String nombre = "Juan Perez";
        String objeto = "Mantas";

        DonacionIndividual mockDonacion = new DonacionIndividual();
        mockDonacion.setDonanteNombre(nombre);

        when(repository.save(any(Donacion.class))).thenReturn(mockDonacion);
        Donacion resultado = service.registrarDonacion(tipo, monto, nombre, objeto, null, null);

        assertNotNull(resultado);
        assertEquals(nombre, resultado.getDonanteNombre());
        verify(repository, times(1)).save(any(Donacion.class));
    }

    @Test
    void cuandoRegistrarDonacionEmpresarial_entoncesRetornaDonacionConRut() {
        String tipo = "EMPRESARIAL";
        Double monto = 5000.0;
        String nombre = "Empresa Tech";
        String objeto = "Computadores";
        String rut = "99.888.777-6";
        String certificado = "CERT-2026";

        com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial mockDonacion = new com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial();
        mockDonacion.setDonanteNombre(nombre);
        mockDonacion.setRutEmpresa(rut);

        when(repository.save(any(com.gestionDonaton.gestion_donaciones.model.Donacion.class))).thenReturn(mockDonacion);

        com.gestionDonaton.gestion_donaciones.model.Donacion resultado = service.registrarDonacion(tipo, monto, nombre, objeto, rut, certificado);
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertTrue(resultado instanceof com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial);
        assertEquals(rut, ((com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial) resultado).getRutEmpresa());
    }
}