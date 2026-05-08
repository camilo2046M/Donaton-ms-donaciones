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
        verify(repository, times(1)).save(any(Donacion.class)); // Verifica que se llamó al repo
    }
}