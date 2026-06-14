package com.gestionDonaton.gestion_donaciones.service;

import com.gestionDonaton.gestion_donaciones.dto.DonacionRequestDTO;
import com.gestionDonaton.gestion_donaciones.dto.DonacionResponseDTO;
import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionIndividual;
import com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial;
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
        // CORREGIDO: Constructor vacío + Setters para evitar el error del Builder
        DonacionRequestDTO requestDto = new DonacionRequestDTO();
        requestDto.setTipo("INDIVIDUAL");
        requestDto.setMonto(100.0);
        requestDto.setNombre("Juan Perez");
        requestDto.setObjeto("Mantas");

        // Comportamiento del modelo interno (Entidad)
        DonacionIndividual mockDonacionEntidad = new DonacionIndividual();
        mockDonacionEntidad.setDonanteNombre("Juan Perez");

        when(repository.save(any(Donacion.class))).thenReturn(mockDonacionEntidad);

        // Invocación del servicio con el DTO
        DonacionResponseDTO resultado = service.registrarDonacion(requestDto);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getDonanteNombre());
        verify(repository, times(1)).save(any(Donacion.class));
    }

    @Test
    void cuandoRegistrarDonacionEmpresarial_entoncesRetornaDonacionConRut() {
        // CORREGIDO: Constructor vacío + Setters para evitar el error del Builder
        DonacionRequestDTO requestDto = new DonacionRequestDTO();
        requestDto.setTipo("EMPRESARIAL");
        requestDto.setMonto(5000.0);
        requestDto.setNombre("Empresa Tech");
        requestDto.setObjeto("Computadores");
        requestDto.setRut("99.888.777-6");
        requestDto.setCertificado("CERT-2026");

        // El repositorio sigue interactuando con las entidades mapeadas de BD
        DonacionEmpresarial mockDonacionEntidad = new DonacionEmpresarial();
        mockDonacionEntidad.setDonanteNombre("Empresa Tech");
        mockDonacionEntidad.setRutEmpresa("99.888.777-6");

        when(repository.save(any(Donacion.class))).thenReturn(mockDonacionEntidad);

        // Llamada adaptada a la firma del servicio
        DonacionResponseDTO resultado = service.registrarDonacion(requestDto);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals("99.888.777-6", resultado.getRutEmpresa());
        assertEquals("Empresa Tech", resultado.getDonanteNombre());
        verify(repository, times(1)).save(any(Donacion.class));
    }
}