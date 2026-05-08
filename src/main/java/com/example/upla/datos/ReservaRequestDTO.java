package com.example.upla.datos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {
    private Date f_entrada;
    private Date f_salida;
    private Long id_cliente;
    private Long id_apartamento;
}
