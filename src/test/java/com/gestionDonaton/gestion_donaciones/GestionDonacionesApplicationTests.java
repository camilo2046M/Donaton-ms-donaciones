package com.gestionDonaton.gestion_donaciones;

import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
// Usamos la misma estrategia ganadora para evitar que H2 se vuelva loco con el contexto global
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:h2:mem:testapplicationdb;DATABASE_TO_UPPER=FALSE;ANALYZE_AUTO=0",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"jakarta.persistence.validation.mode=none"
})
class GestionDonacionesApplicationTests {

	@Test
	void contextLoads() {

	}
}
