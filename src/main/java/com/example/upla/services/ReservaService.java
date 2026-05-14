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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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

        // Validar que el DNI coincida con el del cliente
        if (!clienteEncontrado.getDni().equals(reservaDTO.getDni())) {
            throw new IllegalArgumentException("Error: El DNI proporcionado no coincide con el del cliente registrado.");
        }

        Apartamento apartamentoEncontrado = apartamentoRepository.findById(reservaDTO.getId_apartamento())
                .orElseThrow(() -> new RuntimeException("Error: El apartamento con ID " + reservaDTO.getId_apartamento() + " no existe."));

        Date entrada = reservaDTO.getF_entrada();
        Date salida = reservaDTO.getF_salida();

        if (salida.before(entrada) || salida.equals(entrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }

        if (reservaRepository.existeSolapamiento(reservaDTO.getId_apartamento(), entrada, salida)) {
            throw new IllegalArgumentException("Error: El apartamento ya está reservado para las fechas seleccionadas.");
        }

        Double precioReserva = calcularPrecioReserva(entrada, salida, apartamentoEncontrado.getPrecio(), clienteEncontrado.getRegistrado());

        // Generar ID de reserva: "yyyy-MM-dd-idDepartamento" (usando fecha de entrada)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEntradaFormato = sdf.format(entrada);
        String idReservaBase = fechaEntradaFormato + "-" + String.format("%02d", reservaDTO.getId_apartamento());

        // Verificar si el ID ya existe y agregar sufijo si es necesario
        String idReserva = idReservaBase;
        int contador = 1;
        while (reservaRepository.existsById(idReserva)) {
            idReserva = idReservaBase + "-" + contador;
            contador++;
        }

        Reserva nuevaReserva = Reserva.builder()
                .id_reserva(idReserva)
                .f_entrada(entrada)
                .f_salida(salida)
                .cliente(clienteEncontrado)
                .apartamento(apartamentoEncontrado)
                .precio(precioReserva)
                .build();

        Reserva reservaGuardada = reservaRepository.save(nuevaReserva);

        return ReservaResponseDTO.builder()
                .id_reserva(reservaGuardada.getId_reserva())
                .f_entrada(reservaGuardada.getF_entrada())
                .f_salida(reservaGuardada.getF_salida())
                .nombreCliente(reservaGuardada.getCliente().getNombre())
                .direccionApartamento(reservaGuardada.getApartamento().getDireccion())
                .precio(reservaGuardada.getPrecio())
                .build();
    }

    public Page<ReservaResponseDTO> obtenerTodasLasReservas(int page, int size){
        Pageable configuracionPagina = PageRequest.of(page, size);

        Page<Reserva> paginaDeReservas = reservaRepository.findAll(configuracionPagina);

        return paginaDeReservas.map(reserva -> ReservaResponseDTO.builder()
                .id_reserva(reserva.getId_reserva())
                .f_entrada(reserva.getF_entrada())
                .f_salida(reserva.getF_salida())
                .nombreCliente(reserva.getCliente().getNombre())
                .direccionApartamento(reserva.getApartamento().getDireccion())
                .precio(reserva.getPrecio())
                .build());
    }

    public void eliminarReserva(String id){
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Error: La reserva con ID " + id + " no existe.");
        }
        reservaRepository.deleteById(id);

    }

    public ReservaResponseDTO actualizarReserva(String id, ReservaRequestDTO nuevosDatos) {
        Reserva reservaAntigua = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: La reserva con ID " + id + " no existe."));

        Cliente nuevoClienteEncontrado = clienteRepository.findById(nuevosDatos.getId_cliente())
                .orElseThrow(() -> new RuntimeException("Error: El cliente no existe."));

        // Validar que el DNI coincida con el del cliente
        if (!nuevoClienteEncontrado.getDni().equals(nuevosDatos.getDni())) {
            throw new IllegalArgumentException("Error: El DNI proporcionado no coincide con el del cliente registrado.");
        }

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
        reservaAntigua.setApartamento(nuevoApartamentoEncontrado);

        // Recalcular el precio de la reserva
        Double precioReserva = calcularPrecioReserva(entrada, salida, nuevoApartamentoEncontrado.getPrecio(), nuevoClienteEncontrado.getRegistrado());
        reservaAntigua.setPrecio(precioReserva);

        Reserva reservaGuardada = reservaRepository.save(reservaAntigua);

        return ReservaResponseDTO.builder()
                .id_reserva(reservaGuardada.getId_reserva())
                .f_entrada(reservaGuardada.getF_entrada())
                .f_salida(reservaGuardada.getF_salida())
                .nombreCliente(reservaGuardada.getCliente().getNombre())
                .direccionApartamento(reservaGuardada.getApartamento().getDireccion())
                .precio(reservaGuardada.getPrecio())
                .build();

    }

    @Scheduled (fixedRate = 60000)
    public void eliminarReservasSolapadas() {
        List<Reserva> todasReservas = new ArrayList<>(reservaRepository.findAll());

        for (int i = 0; i < todasReservas.size(); i++){
            Reserva reservaA = todasReservas.get(i);
            for (int j = i + 1; j < todasReservas.size(); j++) {
                Reserva reservaB = todasReservas.get(j);

                if (reservaA.getApartamento().getId_ap().equals(reservaB.getApartamento().getId_ap())){
                    if (reservaB.getF_entrada().before(reservaA.getF_salida()) &&
                            reservaB.getF_salida().after(reservaA.getF_entrada())) {
                        if (reservaA.getId_reserva().compareTo(reservaB.getId_reserva()) > 0) {
                            reservaRepository.deleteById(reservaA.getId_reserva());
                        } else {
                            reservaRepository.deleteById(reservaB.getId_reserva());
                        }
                    }
                }
            }
        }
    }

    /**
     * Calcula el precio total de la reserva basado en el número de noches,
     * precio por noche del apartamento, y descuentos disponibles.
     * Si la reserva es de una sola noche, se añade un 50% de recargo.
     *
     * Los descuentos se aplican por prioridad (no se acumulan):
     * 1. Cliente registrado: 10% descuento
     * 2. Reserva de 7+ noches: 10% descuento
     * 3. Sin descuento
     */
    private Double calcularPrecioReserva(Date f_entrada, Date f_salida, Double precioPorNoche, Boolean clienteRegistrado) {
        long diferencia = f_salida.getTime() - f_entrada.getTime();
        // milisegundos * segundos * minutos * horas
        // con esto podemos calcular el número de días
        long numeroNoches = diferencia / (1000 * 60 * 60 * 24);

        Double precioTotal = numeroNoches * precioPorNoche;

        // Si es una sola noche, aplicar 50% de recargo
        if (numeroNoches == 1) {
            precioTotal = precioTotal * 1.5;
        }

        // Aplicar descuentos por prioridad (NO se acumulan)
        if (clienteRegistrado != null && clienteRegistrado) {
            // Cliente registrado: 10% descuento
            precioTotal = precioTotal * 0.9;
        } else if (numeroNoches >= 7) {
            // Reserva de 7+ noches: 10% descuento (solo si no es cliente registrado)
            precioTotal = precioTotal * 0.9;
        }

        return precioTotal;
    }
}