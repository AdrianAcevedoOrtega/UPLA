package com.example.upla.controllers;

// Esta clase lo que recibe son las peticiones POST

import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.datos.ReservaResponseDTO;
import com.example.upla.services.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reservas")
@RequiredArgsConstructor

public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping
    // Este método que recibe los post, es decir, las creaciones, se encarga de crear una nueva reserva. Recibe un objeto de tipo ReservaRequestDTO que contiene los datos necesarios para crear la reserva, como las fechas de entrada y salida, el ID del cliente y el ID del apartamento. El método valida los datos recibidos utilizando la anotación @Valid y luego llama al servicio de reservas para crear la nueva reserva. Finalmente, devuelve un objeto de tipo ReservaResponseDTO que contiene los detalles de la reserva creada.
    public ReservaResponseDTO crear(@Valid @RequestBody ReservaRequestDTO reservaDTO) {
        return reservaService.crearReserva(reservaDTO);
    }

    /**
     * Pagina todas las reservas.
     * La primera página empieza por 0.
     * La última página empieza por 10.
     */
    @GetMapping
    public Page<ReservaResponseDTO> listar(
            @RequestParam(defaultValue = "0") int page, // Por defecto ponemos que la primera página sea la 0
            @RequestParam(defaultValue = "10") int size) {
        return reservaService.obtenerTodasLasReservas(page, size);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        reservaService.eliminarReserva(id);
    }

    @PutMapping("/{id}")
    // Este metodo recibe los put, es decir, las actualizaciones
    public ReservaResponseDTO actualizar(@PathVariable String id, @Valid @RequestBody ReservaRequestDTO nuevosDatos){
        return reservaService.actualizarReserva(id, nuevosDatos);
    }
}
