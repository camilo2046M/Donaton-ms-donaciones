package com.gestionDonaton.gestion_donaciones.factory;

import com.gestionDonaton.gestion_donaciones.model.*;

public class DonacionFactory {
    public static Donacion crearDonacion(String tipo) {
        if ("EMPRESARIAL".equalsIgnoreCase(tipo)) {
            return new DonacionEmpresarial();
        }
        return new DonacionIndividual();
    }
}
