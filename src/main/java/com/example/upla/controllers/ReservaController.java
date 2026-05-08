package com.example.upla.controllers;

// Esta clase lo que recibe son las peticiones POST

import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.datos.ReservaResponseDTO;
import com.example.upla.models.Reserva;
import com.example.upla.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping
    public ReservaResponseDTO crear(@RequestBody ReservaRequestDTO reservaDTO) {
        return reservaService.crearReserva(reservaDTO);
    }

    @GetMapping
    public List<ReservaResponseDTO> listar() {
        return reservaService.obtenerTodasLasReservas();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    @PutMapping("/{id}")
    public ReservaResponseDTO actualizar(@PathVariable Long id, @RequestBody ReservaRequestDTO nuevosDatos){
        return reservaService.actualizarReserva(id, nuevosDatos);
    }
}
