package com.gestionDonaton.gestion_donaciones.repository;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionIndividual;
import com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        // Configuramos la URL manualmente diciéndole a H2 que no valide de forma tan estricta
        "spring.datasource.url=jdbc:h2:mem:testdb;DATABASE_TO_UPPER=FALSE;ANALYZE_AUTO=0",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "jakarta.persistence.validation.mode=none"
})
class DonacionRepositoryTest {

    @Autowired
    private DonacionRepository donacionRepository;

    @Test
    @DisplayName("Repository - Debería guardar y recuperar una donación individual")
    void guardarYBuscarDonacionIndividual() {
        // 1. Crear entidad llenando todos los campos para evitar cualquier restricción CHECK oculta
        DonacionIndividual donacion = new DonacionIndividual();
        donacion.setDonanteNombre("Andres Flores");
        donacion.setNombreObjeto("Ropa de abrigo");
        donacion.setEstado("PENDIENTE");
        donacion.setMonto(1000.0); // <-- Inicializamos el monto por si tiene un CHECK > 0

        // 2. Persistir
        Donacion guardada = donacionRepository.save(donacion);

        // 3. Validar
        assertNotNull(guardada.getId());
        Optional<Donacion> encontrada = donacionRepository.findById(guardada.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Andres Flores", encontrada.get().getDonanteNombre());
    }

    @Test
    @DisplayName("Repository - Debería guardar y recuperar una donación empresarial con su RUT")
    void guardarYBuscarDonacionEmpresarial() {
        // 1. Crear entidad empresarial completa
        DonacionEmpresarial donacionEmpresa = new DonacionEmpresarial();
        donacionEmpresa.setDonanteNombre("Logística S.A.");
        donacionEmpresa.setNombreObjeto("Cajas de mercadería");
        donacionEmpresa.setRutEmpresa("77.666.555-4");
        donacionEmpresa.setCertificadoImpuestos("CERT-1234");
        donacionEmpresa.setEstado("PENDIENTE");
        donacionEmpresa.setMonto(5000.0); // <-- Inicializamos el monto aquí también

        // 2. Guardar
        Donacion guardada = donacionRepository.save(donacionEmpresa);

        // 3. Validar
        assertNotNull(guardada.getId());
        assertTrue(guardada instanceof DonacionEmpresarial);
        assertEquals("77.666.555-4", ((DonacionEmpresarial) guardada).getRutEmpresa());
    }

    @Test
    @DisplayName("Repository - Debería filtrar donaciones ignorando mayúsculas y minúsculas")
    void buscarPorPalabraClave() {
        // 1. Insertar datos de prueba consistentes
        DonacionIndividual d1 = new DonacionIndividual();
        d1.setDonanteNombre("Carlos");
        d1.setNombreObjeto("Alimento para mascotas");
        d1.setEstado("PENDIENTE");
        d1.setMonto(2000.0);
        donacionRepository.save(d1);

        DonacionIndividual d2 = new DonacionIndividual();
        d2.setDonanteNombre("Marta");
        d2.setNombreObjeto("Medicamentos vencidos");
        d2.setEstado("PENDIENTE");
        d2.setMonto(3000.0);
        donacionRepository.save(d2);

        // 2. Ejecutar la consulta de tu interfaz
        List<Donacion> resultados = donacionRepository.findByNombreObjetoContainingIgnoreCase("alimento");

        // 3. Validar
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Alimento para mascotas", resultados.get(0).getNombreObjeto());
    }
}