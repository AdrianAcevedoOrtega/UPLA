package com.example.upla.services;

import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.datos.ReservaResponseDTO;
import com.example.upla.models.Reserva;
import com.example.upla.models.enums.ReservaStatus;
import com.example.upla.mappers.ReservaMapper;
import com.example.upla.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    @Transactional
    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        String apartmentId = "Apto ID: " + dto.getId_apartamento();

        if (reservaRepository.existsOverlappingReserva(apartmentId, dto.getF_entrada(), dto.getF_salida())) {
            throw new RuntimeException("El apartamento ya está ocupado en esas fechas.");
        }

        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setStatus(ReservaStatus.CONFIRMED);
        return reservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaResponseDTO actualizarReserva(String id, ReservaRequestDTO dto) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        String apartmentId = "Apto ID: " + dto.getId_apartamento();

        boolean overlap = reservaRepository.existsOverlappingReservaExcludingSelf(
                apartmentId, id, dto.getF_entrada(), dto.getF_salida());

        if (overlap) {
            throw new RuntimeException("Conflicto de fechas: el apartamento ya está reservado.");
        }

        // Actualizamos los campos
        reserva.setClienteNombre("DNI: " + dto.getDni());
        reserva.setHabitacionTipo(apartmentId);
        reserva.setFechaEntrada(dto.getF_entrada());
        reserva.setFechaSalida(dto.getF_salida());

        return reservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public void cancelarReserva(String id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setStatus(ReservaStatus.CANCELLED);
        reservaRepository.save(reserva);
    }

    @Transactional
    public void eliminarFisicamente(String id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("El ID no existe en la base de datos.");
        }
        reservaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarTodas() {
        return reservaRepository.findAll().stream()
                .map(reservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}