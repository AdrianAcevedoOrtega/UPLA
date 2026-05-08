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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reserva;

    @Column(nullable = false)
    private Date f_entrada;

    @Column(nullable = false)
    private Date f_salida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ap")
    private Apartamento apartmento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}