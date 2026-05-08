package com.example.upla.controllers;

// Esta clase lo que recibe son las peticiones POST

import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.datos.ReservaResponseDTO;
import com.example.upla.models.Reserva;
import com.example.upla.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping
    public ReservaResponseDTO crear(@RequestBody ReservaRequestDTO reservaDTO) {
        return reservaService.crearReserva(reservaDTO);
    }

}
