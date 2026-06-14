package com.gestionDonaton.gestion_donaciones.service;
import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionIndividual;
import com.gestionDonaton.gestion_donaciones.repository.DonacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonacionServiceTest {

    @Mock
    private DonacionRepository repository; // Simula el repositorio

    @InjectMocks
    private DonacionService service; // Inyecta el mock del repositorio dentro del servicio

    @Test
    @DisplayName("Debería retornar true y cambiar el estado a COMPLETADA si la donación existe")
    void actualizarEstadoCompletadoExitoso() {
        // Arrange (Preparar los datos simulados)
        Long idExistente = 1L;
        Donacion donacionSimulada = new DonacionIndividual();
        donacionSimulada.setId(idExistente);
        donacionSimulada.setEstado("PENDIENTE");

        // Le decimos a Mockito: cuando busquen el ID 1, devuelve la donación simulada
        when(repository.findById(idExistente)).thenReturn(Optional.of(donacionSimulada));

        // Act (Ejecutar el método real del servicio)
        boolean resultado = service.actualizarEstadoCompletado(idExistente);

        // Assert (Verificar el comportamiento y los resultados)
        assertTrue(resultado, "El método debería retornar true");
        assertEquals("COMPLETADA", donacionSimulada.getEstado(), "El estado interno debió cambiar a COMPLETADA");

        // Verifica que el repositorio efectivamente intentó guardar los cambios
        verify(repository, times(1)).save(donacionSimulada);
    }

    @Test
    @DisplayName("Debería retornar false si la donación no existe en la base de datos")
    void actualizarEstadoCompletadoNoEncontrado() {
        // Arrange
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act
        boolean resultado = service.actualizarEstadoCompletado(idInexistente);

        // Assert
        assertFalse(resultado, "Debería retornar false al no encontrar el ID");
        verify(repository, never()).save(any(Donacion.class)); // Asegura que nunca se guardó nada
    }

    @Test
    @DisplayName("Debería listar todas las donaciones del repositorio")
    void listarTodasLasDonaciones() {
        // Arrange
        List<Donacion> listaSimulada = new ArrayList<>();
        listaSimulada.add(new DonacionIndividual());
        listaSimulada.add(new DonacionIndividual());
        when(repository.findAll()).thenReturn(listaSimulada);

        // Act
        List<Donacion> resultado = service.listarTodas();

        // Assert
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería buscar donaciones que contengan una palabra clave")
    void buscarPorPalabraClave() {
        // Arrange
        String palabra = "Alimento";
        List<Donacion> listaFiltrada = new ArrayList<>();
        listaFiltrada.add(new DonacionIndividual());
        when(repository.findByNombreObjetoContainingIgnoreCase(palabra)).thenReturn(listaFiltrada);

        // Act
        List<Donacion> resultado = service.buscarPorPalabra(palabra);

        // Assert
        assertFalse(resultado.isEmpty());
        verify(repository, times(1)).findByNombreObjetoContainingIgnoreCase(palabra);
    }
}