package com.gestionDonaton.gestion_donaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.gestionDonaton.gestion_donaciones.repository")

@EntityScan(basePackages = "com.gestionDonaton.gestion_donaciones.model")
public class GestionDonacionesApplication {

	public static void main(String[] args) {

		SpringApplication.run(GestionDonacionesApplication.class, args);
		System.out.println(">>> Microservicio de Gestión de Donaciones de Donaton operativo en puerto 8081");
	}

}
