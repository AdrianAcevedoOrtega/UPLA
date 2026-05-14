package com.example.upla.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Builder
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Boolean registrado = false;

    @Column(nullable = false, unique = true)
    private String dni;

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default // Esto se ha añadido el 14/05/2026
    private Set<Reserva> reservas = new HashSet<>();
}