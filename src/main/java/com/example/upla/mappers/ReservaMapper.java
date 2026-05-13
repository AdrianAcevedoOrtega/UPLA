package com.example.upla.mappers;




import com.example.upla.models.Reserva;
import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.datos.ReservaResponseDTO;
import com.example.upla.models.enums.ReservaStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservaMapper {

    /**
     * Converts a Request DTO from the frontend into a Database Entity.
     */
    public Reserva toEntity(ReservaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Reserva reserva = new Reserva();
        // ID and CreatedAt are handled by @PrePersist in the Entity class
        reserva.setClienteNombre("DNI: " + dto.getDni());
        reserva.setHabitacionTipo("Apto ID: " + dto.getId_apartamento());
        reserva.setFechaEntrada(dto.getF_entrada());
        reserva.setFechaSalida(dto.getF_salida());

        // Defaulting to PENDING until the Service logic confirms the schedule
        reserva.setStatus(ReservaStatus.PENDING);

        return reserva;
    }

    /**
     * Converts a Database Entity into a Response DTO for the frontend.
     */
    public ReservaResponseDTO toResponseDTO(Reserva entity) {
        if (entity == null) {
            return null;
        }

        return ReservaResponseDTO.builder()
                .id_reserva(entity.getId())
                .nombreCliente(entity.getClienteNombre())
                .f_entrada(entity.getFechaEntrada())
                .f_salida(entity.getFechaSalida())
                .direccionApartamento(entity.getHabitacionTipo())
                .build();
    }
}
