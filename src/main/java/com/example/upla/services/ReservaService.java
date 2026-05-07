package com.example.upla.services;

import com.example.upla.datos.ReservaRequestDTO;
import com.example.upla.models.Apartamento;
import com.example.upla.models.Cliente;
import com.example.upla.repositories.ApartamentoRepository;
import com.example.upla.repositories.ClienteRepository;
import com.example.upla.repositories.ReservaRepository;
import com.example.upla.models.Reserva;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final ApartamentoRepository apartamentoRepository;

    // Métodos
    public Reserva crearReserva(ReservaRequestDTO reservaDTO) {
        Cliente cliente = new Cliente();
        Apartamento apartamento = new Apartamento();
        Date entrada = reservaDTO.getF_entrada();
        Date salida = reservaDTO.getF_salida();
        // Comprobamos que la fecha de salida no sea anterior a la fecha de entrada
        // no que las fechas sean iguales
        if (salida.before(entrada) || salida.equals(entrada)) {
            throw new IllegalArgumentException("La fecha de salida no puede ser anterior o igual a la fecha de entrada.");
        }

        Cliente clienteEncontrado = clienteRepository.findById(reservaDTO.getId_cliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Reserva reserva = Reserva.builder().f_entrada(entrada)
                .f_salida(salida)
                .cliente(clienteEncontrado)
                .apartamento(apartamentoRepository.findById(reservaDTO.getId_apartamento())
                        .orElseThrow(() -> new RuntimeException("Apartamento no encontrado")))
                .build();

        // Save lo que hace es que comprueba la id del objeto reservaDTO
        // En caso de que sea 0 o nulo ejecuta un insert en la base de datos, en caso de que tenga un id existente ejecuta un update
        return reservaRepository.save(reservaParaGuardar);
    }
}
