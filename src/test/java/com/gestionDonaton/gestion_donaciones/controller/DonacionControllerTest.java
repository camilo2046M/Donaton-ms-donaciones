package com.gestionDonaton.gestion_donaciones.controller;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
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

    // Clase estática interna para aislar el contexto web y evitar que Spring levante JPA/Hibernate
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
        // CORREGIDO: Usamos el Stub real en lugar de Mockito para evitar recursión infinita en el JSON
        Donacion donacionSimulada = new DonacionTestStub(1L, "Carlos", "Dinero");

        // Configuramos el comportamiento del servicio mockeado
        when(service.registrarDonacion(
                eq("INDIVIDUAL"), eq(15000.0), eq("Carlos"),
                eq("Dinero"), eq("12345678-9"), eq("SI")
        )).thenReturn(donacionSimulada);

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

        verify(service, times(1)).registrarDonacion("INDIVIDUAL", 15000.0, "Carlos", "Dinero", "12345678-9", "SI");
    }

    // ==========================================
    // 2. TESTS: PATCH /api/donaciones/{id}/completar
    // ==========================================
    @Test
    @DisplayName("PATCH /api/donaciones/{id}/completar - Debería retornar 200 OK cuando se actualiza con éxito")
    void marcarComoCompletadaExitoso() throws Exception {
        Long idExistente = 1L;
        when(service.actualizarEstadoCompletado(idExistente)).thenReturn(true);

        mockMvc.perform(patch("/api/donaciones/{id}/completar", idExistente))
                .andExpect(status().isOk())
                .andExpect(content().string("Donación " + idExistente + " COMPLETADA"));

        verify(service, times(1)).actualizarEstadoCompletado(idExistente);
    }

    @Test
    @DisplayName("PATCH /api/donaciones/{id}/completar - Debería retornar 404 NOT FOUND si el ID no existe")
    void marcarComoCompletadaFallido() throws Exception {
        Long idInexistente = 99L;
        when(service.actualizarEstadoCompletado(idInexistente)).thenReturn(false);

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
        List<Donacion> listaResultados = new ArrayList<>();

        // CORREGIDO: Usamos el Stub para que Jackson pueda serializar la lista correctamente
        Donacion donacionMock = new DonacionTestStub(2L, "Anónimo", "Alimento no perecible");
        listaResultados.add(donacionMock);

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
        List<Donacion> todasLasDonaciones = new ArrayList<>();

        // CORREGIDO: Cambiado de mock() a la implementación Stub limpia
        Donacion donacionMock = new DonacionTestStub(10L, "Juan", "Ropa");
        todasLasDonaciones.add(donacionMock);

        when(service.listarTodas()).thenReturn(todasLasDonaciones);

        mockMvc.perform(get("/api/donaciones/listar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(10));

        verify(service, times(1)).listarTodas();
    }

    // Clase concreta de prueba para poder instanciar la clase abstracta limpiamente sin Mockito
    static class DonacionTestStub extends Donacion {
        private Long id;
        private String donanteNombre;
        private String nombreObjeto;

        public DonacionTestStub(Long id, String donanteNombre, String nombreObjeto) {
            this.id = id;
            this.donanteNombre = donanteNombre;
            this.nombreObjeto = nombreObjeto;
        }

        @Override
        public Long getId() { return this.id; }

        @Override
        public String getDonanteNombre() { return this.donanteNombre; }

        @Override
        public String getNombreObjeto() { return this.nombreObjeto; }
    }
}