package com.example.upla.services;

import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.datos.ReservaResponseDTO;
import com.example.upla.models.Apartamento;
import com.example.upla.models.Cliente;
import com.example.upla.models.Reserva;
import com.example.upla.repositories.ApartamentoRepository;
import com.example.upla.repositories.ClienteRepository;
import com.example.upla.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final ApartamentoRepository apartamentoRepository;

    // Métodos
    public ReservaResponseDTO crearReserva(ReservaRequestDTO reservaDTO) {

        Cliente clienteEncontrado = clienteRepository.findById(reservaDTO.getId_cliente())
                .orElseThrow(() -> new RuntimeException("Error: El cliente con ID " + reservaDTO.getId_cliente() + " no existe."));

        Apartamento apartamentoEncontrado = apartamentoRepository.findById(reservaDTO.getId_apartamento())
                .orElseThrow(() -> new RuntimeException("Error: El apartamento con ID " + reservaDTO.getId_apartamento() + " no existe."));

        Date entrada = reservaDTO.getF_entrada();
        Date salida = reservaDTO.getF_salida();

        if (salida.before(entrada) || salida.equals(entrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }

        Reserva nuevaReserva = Reserva.builder()
                .f_entrada(entrada)
                .f_salida(salida)
                .cliente(clienteEncontrado)
                .apartmento(apartamentoEncontrado)
                .build();

        Reserva reservaGuardada = reservaRepository.save(nuevaReserva);

        return ReservaResponseDTO.builder()
                .id_reserva(reservaGuardada.getId_reserva())
                .f_entrada(reservaGuardada.getF_entrada())
                .f_salida(reservaGuardada.getF_salida())
                .nombreCliente(reservaGuardada.getCliente().getNombre()) // Sacamos el nombre del objeto Cliente
                .direccionApartamento(reservaGuardada.getApartmento().getDireccion()) // Sacamos la dirección del objeto Apartamento
                .build();
    }

    public List<ReservaResponseDTO> obtenerTodasLasReservas(){
        List<ReservaResponseDTO> reservas = new ArrayList<>();
        List<Reserva> reservasEnBaseDeDatos = reservaRepository.findAll();;
        for (Reserva reserva: reservasEnBaseDeDatos){
            ReservaResponseDTO dato = ReservaResponseDTO.builder()
                    .id_reserva(reserva.getId_reserva())
                    .f_entrada(reserva.getF_entrada())
                    .f_salida(reserva.getF_salida())
                    .nombreCliente(reserva.getCliente().getNombre()) // Sacamos el nombre del objeto Cliente
                    .direccionApartamento(reserva.getApartmento().getDireccion()) // Sacamos la dirección del objeto Apartamento
                    .build();

            reservas.add(dato);
        }

        return reservas;

    }

    public void eliminarReserva(Long id){
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Error: La reserva con ID " + id + " no existe.");
        }
        reservaRepository.deleteById(id);

    }

    public ReservaResponseDTO actualizarReserva(Long id, ReservaRequestDTO nuevosDatos) {
        Reserva reservaAntigua = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: La reserva con ID " + id + " no existe."));

        Cliente nuevoClienteEncontrado = clienteRepository.findById(nuevosDatos.getId_cliente())
                .orElseThrow(() -> new RuntimeException("Error: El cliente no existe."));

        Apartamento nuevoApartamentoEncontrado = apartamentoRepository.findById(nuevosDatos.getId_apartamento())
                .orElseThrow(() -> new RuntimeException("Error: El apartamento no existe."));

        Date entrada = nuevosDatos.getF_entrada();
        Date salida = nuevosDatos.getF_salida();
        if (salida.before(entrada) || salida.equals(entrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }

        reservaAntigua.setF_entrada(entrada);
        reservaAntigua.setF_salida(salida);
        reservaAntigua.setCliente(nuevoClienteEncontrado);
        reservaAntigua.setApartmento(nuevoApartamentoEncontrado);

        Reserva reservaGuardada = reservaRepository.save(reservaAntigua);

        return ReservaResponseDTO.builder()
                .id_reserva(reservaGuardada.getId_reserva())
                .f_entrada(reservaGuardada.getF_entrada())
                .f_salida(reservaGuardada.getF_salida())
                .nombreCliente(reservaGuardada.getCliente().getNombre())
                .direccionApartamento(reservaGuardada.getApartmento().getDireccion())
                .build();

    }
}