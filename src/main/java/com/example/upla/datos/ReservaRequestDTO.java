package com.example.upla.datos;

import com.example.upla.models.Reserva;
import com.example.upla.services.ReservaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
