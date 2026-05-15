package com.example.upla.datos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDTO {

    private String id_reserva;
    private String nombreCliente;
    private LocalDateTime f_entrada;
    private LocalDateTime f_salida;
    private String direccionApartamento;
    private Double precio;


}
