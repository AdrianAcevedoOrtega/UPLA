package com.example.upla.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Builder
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    private String id_reserva;

    @Column(nullable = false)
    private Date f_entrada;

    @Column(nullable = false)
    private Date f_salida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ap")
    private Apartamento apartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}