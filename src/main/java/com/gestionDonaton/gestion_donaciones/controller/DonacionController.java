package com.gestionDonaton.gestion_donaciones.controller;
import com.gestionDonaton.gestion_donaciones.dto.DonacionResponseDTO;
import com.gestionDonaton.gestion_donaciones.service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@Tag(name = "Gestión de Donaciones", description = "Endpoints para el registro, actualización y consulta de donaciones")
public class DonacionController {

    @Autowired
    private DonacionService service;

    // ==========================================
    // 1. ENDPOINT: POST /api/donaciones/crear
    // ==========================================
    @PostMapping("/crear")
    @Operation(
            summary = "Registrar una nueva donación",
            description = "Crea un registro de donación en el sistema. Soporta tanto donaciones monetarias como de objetos físicos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Donación registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos")
    })
    public ResponseEntity<DonacionResponseDTO> crearDonacion(
            @Parameter(description = "Tipo de donación (ej. Moneda, Alimento, Ropa)", required = true)
            @RequestParam String tipo,

            @Parameter(description = "Monto de la donación (0 si es un objeto físico)", required = true)
            @RequestParam Double monto,

            @Parameter(description = "Nombre del donante", required = true)
            @RequestParam String nombre,

            @Parameter(description = "Descripción del objeto donado (en caso de aplicar)", required = true)
            @RequestParam String objeto,

            @Parameter(description = "RUT del donante (opcional para extranjeros o anónimos)", required = false)
            @RequestParam(required = false) String rut,

            @Parameter(description = "Indica si requiere certificado de donación (opcional)", required = false)
            @RequestParam(required = false) String certificado) {

        // 1. Creamos el objeto RequestDTO vacío usando el constructor sin argumentos
        com.gestionDonaton.gestion_donaciones.dto.DonacionRequestDTO requestDto =
                new com.gestionDonaton.gestion_donaciones.dto.DonacionRequestDTO();

        // 2. Le asignamos los valores mediante setters
        requestDto.setTipo(tipo);
        requestDto.setMonto(monto);
        requestDto.setNombre(nombre);
        requestDto.setObjeto(objeto);
        requestDto.setRut(rut);
        requestDto.setCertificado(certificado);

        // 3. Declaramos correctamente la variable 'nuevaDonacion' pasándole el DTO al servicio
        DonacionResponseDTO nuevaDonacion = service.registrarDonacion(requestDto);

        // 4. Retornamos la respuesta (¡aquí es donde ya no te marcará error!)
        return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
    }

    // ==========================================
    // 2. ENDPOINT: PATCH /api/donaciones/{id}/completar
    // ==========================================
    @PatchMapping("/{id}/completar")
    @Operation(
            summary = "Marcar donación como completada",
            description = "Cambia el estado de una donación específica a 'COMPLETADA' utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna donación con el ID proporcionado")
    })
    public ResponseEntity<String> marcarComoCompletada(
            @Parameter(description = "ID único de la donación a actualizar", required = true)
            @PathVariable Long id) {

        // CORREGIDO: El servicio retorna un DonacionResponseDTO en lugar de un boolean
        DonacionResponseDTO actualizado = service.actualizarEstadoCompletado(id);

        if (actualizado != null) {
            return ResponseEntity.ok("Donación " + id + " COMPLETADA");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID no encontrado");
        }
    }

    // ==========================================
    // 3. ENDPOINT: GET /api/donaciones/buscar/{palabra}
    // ==========================================
    @GetMapping("/buscar/{palabra}")
    @Operation(
            summary = "Buscar donaciones por palabra clave",
            description = "Retorna una lista de donaciones que coincidan con el término de búsqueda en sus campos de texto."
    )
    @ApiResponse(responseCode = "200", description = "Búsqueda procesada (puede retornar una lista vacía)")
    public ResponseEntity<List<DonacionResponseDTO>> buscarPorPalabra(
            @Parameter(description = "Palabra o término clave para filtrar las donaciones", required = true)
            @PathVariable String palabra) {

        // CORREGIDO: Cambiado el tipo de retorno de Donacion a DonacionResponseDTO
        List<DonacionResponseDTO> resultados = service.buscarPorPalabra(palabra);
        return ResponseEntity.ok(resultados);
    }

    // ==========================================
    // 4. ENDPOINT: GET /api/donaciones/listar
    // ==========================================
    @GetMapping("/listar")
    @Operation(
            summary = "Obtener todas las donaciones",
            description = "Recupera un listado completo de todas las donaciones registradas en la base de datos."
    )
    @ApiResponse(responseCode = "200", description = "Listado recuperado exitosamente")
    public ResponseEntity<List<DonacionResponseDTO>> obtenerTodas() {

        // CORREGIDO: Cambiado el tipo de retorno de Donacion a DonacionResponseDTO
        return ResponseEntity.ok(service.listarTodas());
    }
}