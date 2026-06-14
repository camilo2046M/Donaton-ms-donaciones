package com.gestionDonaton.gestion_donaciones.repository;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionIndividual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DonacionRepositoryTest {

    @Autowired
    private DonacionRepository repository;

    @Test
    void buscarPorNombreObjetoExitoso() {
        // Arrange
        DonacionIndividual d1 = new DonacionIndividual();
        d1.setNombreObjeto("Caja de Alimentos");
        d1.setMonto(1500.0);
        d1.setDonanteNombre("Pedro");
        d1.setEstado("PENDIENTE");

        DonacionIndividual d2 = new DonacionIndividual();
        d2.setNombreObjeto("ALIMENTOS perecederos");
        d2.setMonto(500.0);
        d2.setDonanteNombre("Juan");
        d2.setEstado("PENDIENTE");

        // Act
        repository.save(d1);
        repository.save(d2);

        List<Donacion> resultados = repository.findByNombreObjetoContainingIgnoreCase("alimento");

        // Assert
        assertNotNull(resultados);
        assertEquals(2, resultados.size(), "Deberían encontrarse exactamente 2 donaciones");
    }
}