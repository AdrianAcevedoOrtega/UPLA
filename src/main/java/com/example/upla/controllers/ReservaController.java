package com.example.upla.controllers;

// Esta clase lo que recibe son las peticiones POST

import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.datos.ReservaResponseDTO;
import com.example.upla.models.Reserva;
import com.example.upla.services.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping
    // Este metodo recibe los post, es decir, las creaciones
    public ReservaResponseDTO crear(@Valid @RequestBody ReservaRequestDTO reservaDTO) {
        return reservaService.crearReserva(reservaDTO);
    }

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
