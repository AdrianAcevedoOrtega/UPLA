package com.example.upla.datos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReservaResponseDTO {

    // Atributos
    private String id_reserva;
    private String nombreCliente;
    private Date f_entrada;
    private Date f_salida;
    private String direccionApartamento;
    private Double precio;


}
