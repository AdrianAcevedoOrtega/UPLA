package com.example.upla.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ValueGenerationType;

import java.util.List;

@Entity
@Builder
@Table (name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;

    @Column (nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "cliente")
    private List<Reserva> reservas;
}
