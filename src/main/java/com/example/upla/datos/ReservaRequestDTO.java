package com.example.upla.datos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    private String id_reserva;

    @NotBlank(message = "El DNI no puede estar vacío")
    private String dni;

    @NotNull(message = "La fecha de entrada no puede ser nula")
    @FutureOrPresent(message = "La fecha de entrada no puede ser en el pasado")
    private LocalDateTime f_entrada;

    @NotNull(message = "La fecha de salida no puede ser nula")
    private LocalDateTime f_salida;

    @NotNull(message = "El id del cliente no puede ser nulo")
    private Long id_cliente;

    @NotNull(message = "El id del departamento no puede ser nulo")
    private Long id_apartamento;
}