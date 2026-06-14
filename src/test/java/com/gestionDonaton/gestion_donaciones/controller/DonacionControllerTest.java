package com.gestionDonaton.gestion_donaciones.controller;

import com.gestionDonaton.gestion_donaciones.dto.DonacionRequestDTO;
import com.gestionDonaton.gestion_donaciones.dto.DonacionResponseDTO;
import com.gestionDonaton.gestion_donaciones.service.DonacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DonacionController.class)
class DonacionControllerTest {

    @Configuration
    @Import(DonacionController.class)
    static class ContextoAisladoConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DonacionService service;

    // ==========================================
    // 1. TEST: POST /api/donaciones/crear
    // ==========================================
    @Test
    @DisplayName("POST /api/donaciones/crear - Debería registrar una donación exitosamente")
    void crearDonacionExitoso() throws Exception {
        // CORREGIDO: Usamos .builder() para evitar el constructor no público
        DonacionResponseDTO respuestaSimulada = DonacionResponseDTO.builder()
                .id(1L)
                .donanteNombre("Carlos")
                .nombreObjeto("Dinero")
                .build();

        when(service.registrarDonacion(any(DonacionRequestDTO.class))).thenReturn(respuestaSimulada);

        mockMvc.perform(post("/api/donaciones/crear")
                        .param("tipo", "INDIVIDUAL")
                        .param("monto", "15000.0")
                        .param("nombre", "Carlos")
                        .param("objeto", "Dinero")
                        .param("rut", "12345678-9")
                        .param("certificado", "SI")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.donanteNombre").value("Carlos"))
                .andExpect(jsonPath("$.nombreObjeto").value("Dinero"));

        verify(service, times(1)).registrarDonacion(any(DonacionRequestDTO.class));
    }

    // ==========================================
    // 2. TESTS: PATCH /api/donaciones/{id}/completar
    // ==========================================
    @Test
    @DisplayName("PATCH /api/donaciones/{id}/completar - Debería retornar 200 OK cuando se actualiza con éxito")
    void marcarComoCompletadaExitoso() throws Exception {
        Long idExistente = 1L;

        // CORREGIDO: Usamos .builder() para simular el DTO de respuesta
        DonacionResponseDTO respuestaSimulada = DonacionResponseDTO.builder()
                .id(idExistente)
                .build();

        when(service.actualizarEstadoCompletado(idExistente)).thenReturn(respuestaSimulada);

        mockMvc.perform(patch("/api/donaciones/{id}/completar", idExistente))
                .andExpect(status().isOk())
                .andExpect(content().string("Donación " + idExistente + " COMPLETADA"));

        verify(service, times(1)).actualizarEstadoCompletado(idExistente);
    }

    @Test
    @DisplayName("PATCH /api/donaciones/{id}/completar - Debería retornar 404 NOT FOUND si el ID no existe")
    void marcarComoCompletadaFallido() throws Exception {
        Long idInexistente = 99L;

        when(service.actualizarEstadoCompletado(idInexistente)).thenReturn(null);

        mockMvc.perform(patch("/api/donaciones/{id}/completar", idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ID no encontrado"));

        verify(service, times(1)).actualizarEstadoCompletado(idInexistente);
    }

    // ==========================================
    // 3. TEST: GET /api/donaciones/buscar/{palabra}
    // ==========================================
    @Test
    @DisplayName("GET /api/donaciones/buscar/{palabra} - Debería retornar resultados filtrados")
    void buscarPorPalabraClave() throws Exception {
        String terminoBusqueda = "Alimento";
        List<DonacionResponseDTO> listaResultados = new ArrayList<>();

        // CORREGIDO: Usamos .builder() en la lista mockeada
        DonacionResponseDTO dtoMock = DonacionResponseDTO.builder()
                .id(2L)
                .nombreObjeto("Alimento no perecible")
                .build();
        listaResultados.add(dtoMock);

        when(service.buscarPorPalabra(terminoBusqueda)).thenReturn(listaResultados);

        mockMvc.perform(get("/api/donaciones/buscar/{palabra}", terminoBusqueda))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].nombreObjeto").value("Alimento no perecible"));

        verify(service, times(1)).buscarPorPalabra(terminoBusqueda);
    }

    // ==========================================
    // 4. TEST: GET /api/donaciones/listar
    // ==========================================
    @Test
    @DisplayName("GET /api/donaciones/listar - Debería retornar la lista completa en formato JSON")
    void obtenerTodasLasDonaciones() throws Exception {
        List<DonacionResponseDTO> todasLasDonaciones = new ArrayList<>();

        // CORREGIDO: Usamos .builder() para este DTO simulado
        DonacionResponseDTO dtoMock = DonacionResponseDTO.builder()
                .id(10L)
                .build();
        todasLasDonaciones.add(dtoMock);

        when(service.listarTodas()).thenReturn(todasLasDonaciones);

        mockMvc.perform(get("/api/donaciones/listar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(10));

        verify(service, times(1)).listarTodas();
    }
}