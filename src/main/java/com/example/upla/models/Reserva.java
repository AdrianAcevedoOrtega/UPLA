package com.example.upla.models;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionId;

import java.util.List;

@Entity
@Builder
@Table (name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id_reserva;

    @Column(nullable = false)
    private Long id_cliente;

    @Column(nullable = false)
    private Long id_apartamento;

    @Column(nullable = false)
    private Date f_entrada;

    @Column(nullable = false)
    private Date f_salida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartamento apartment;

    @ManyToOne(fetch = FetchType.LAZY) // Good for performance
    @JoinColumn(name = "user_id")
    private Cliente cliente;


}
