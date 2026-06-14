package com.gestionDonaton.gestion_donaciones.Factory;
import com.gestionDonaton.gestion_donaciones.factory.DonacionFactory;
import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial;
import com.gestionDonaton.gestion_donaciones.model.DonacionIndividual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DonacionFactoryTest {

    @Test
    @DisplayName("Debería retornar DonacionEmpresarial cuando el tipo es 'EMPRESARIAL'")
    void deberiaCrearDonacionEmpresarial() {
        Donacion resultado = DonacionFactory.crearDonacion("EMPRESARIAL");
        assertNotNull(resultado);
        assertTrue(resultado instanceof DonacionEmpresarial, "Debería ser una instancia de DonacionEmpresarial");
    }

    @Test
    @DisplayName("Debería retornar DonacionIndividual cuando el tipo es 'INDIVIDUAL'")
    void deberiaCrearDonacionIndividual() {
        Donacion resultado = DonacionFactory.crearDonacion("INDIVIDUAL");
        assertNotNull(resultado);
        assertTrue(resultado instanceof DonacionIndividual, "Debería ser una instancia de DonacionIndividual");
    }

    @Test
    @DisplayName("Debería retornar DonacionIndividual por defecto si el tipo es cualquier otra palabra")
    void deberiaRetornarIndividualPorDefecto() {
        Donacion resultado = DonacionFactory.crearDonacion("CUALQUIER_OTRO_TIPO");
        assertNotNull(resultado);
        assertTrue(resultado instanceof DonacionIndividual, "Cualquier tipo desconocido debe caer en DonacionIndividual según la lógica actual");
    }
}