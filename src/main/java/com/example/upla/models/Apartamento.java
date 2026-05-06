package com.example.upla.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Table (name = "apartamento")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Apartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ap;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private Long CIF;

    @ManyToMany(mappedBy = "apartments") // Points to the field name in Administrator class
    private List<Administrador> administrators;

    @OneToMany(mappedBy = "apartament")
    private List<Reserva> reservas;



}