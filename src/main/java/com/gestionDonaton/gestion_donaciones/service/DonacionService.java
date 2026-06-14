package com.gestionDonaton.gestion_donaciones.service;

import com.gestionDonaton.gestion_donaciones.dto.DonacionRequestDTO;
import com.gestionDonaton.gestion_donaciones.dto.DonacionResponseDTO;
import com.gestionDonaton.gestion_donaciones.factory.DonacionFactory;
import com.gestionDonaton.gestion_donaciones.model.Donacion;
import com.gestionDonaton.gestion_donaciones.model.DonacionEmpresarial;
import com.gestionDonaton.gestion_donaciones.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repository;

    /**
     * Registra una nueva donación usando el patrón Factory.
     * Persiste en PostgreSQL y retorna el DTO de respuesta.
     */
    public DonacionResponseDTO registrarDonacion(DonacionRequestDTO request) {
        // Factory decide si crear DonacionIndividual o DonacionEmpresarial
        Donacion donacion = DonacionFactory.crearDonacion(request.getTipo());

        donacion.setMonto(request.getMonto());
        donacion.setDonanteNombre(request.getNombre());
        donacion.setNombreObjeto(request.getObjeto());
        donacion.setEstado("PENDIENTE");

        // Campos exclusivos de donación empresarial
        if (donacion instanceof DonacionEmpresarial emp) {
            emp.setRutEmpresa(request.getRut());
            emp.setCertificadoImpuestos(request.getCertificado());
        }

        Donacion guardada = repository.save(donacion);
        return toDTO(guardada);
    }

    public List<DonacionResponseDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DonacionResponseDTO> buscarPorPalabra(String palabra) {
        return repository.findByNombreObjetoContainingIgnoreCase(palabra)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DonacionResponseDTO actualizarEstadoCompletado(Long id) {
        Optional<Donacion> optional = repository.findById(id);
        if (optional.isPresent()) {
            Donacion donacion = optional.get();
            donacion.setEstado("COMPLETADA");
            return toDTO(repository.save(donacion));
        }
        return null;
    }

    // ── Mapper entidad → DTO ──────────────────────────────────────────────────
    private DonacionResponseDTO toDTO(Donacion d) {
        DonacionResponseDTO.DonacionResponseDTOBuilder builder = DonacionResponseDTO.builder()
                .id(d.getId())
                .monto(d.getMonto())
                .donanteNombre(d.getDonanteNombre())
                .nombreObjeto(d.getNombreObjeto())
                .estado(d.getEstado());

        if (d instanceof DonacionEmpresarial emp) {
            builder.tipo("EMPRESARIAL")
                    .rutEmpresa(emp.getRutEmpresa())
                    .certificadoImpuestos(emp.getCertificadoImpuestos());
        } else {
            builder.tipo("INDIVIDUAL");
        }

        return builder.build();
    }
}