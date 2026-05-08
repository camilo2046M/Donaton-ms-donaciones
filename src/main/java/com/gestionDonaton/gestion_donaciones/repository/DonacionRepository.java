package com.gestionDonaton.gestion_donaciones.repository;

import com.gestionDonaton.gestion_donaciones.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {

}
