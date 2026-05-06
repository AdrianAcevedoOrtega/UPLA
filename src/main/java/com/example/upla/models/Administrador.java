package com.example.upla.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Table (name = "administradores")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id_ad;

    @Column (nullable = false)
    private String dni;

    @Column (nullable = false)
    private  String nombre;

    @Column (nullable = false)
    private String apellidos;

    @Column (nullable = false)
    private  Long n_ss;

    @ManyToMany
    @JoinTable(
            name = "administrator_apartment", // The name of the join table in MySQL
            joinColumns = @JoinColumn(name = "id_ad"),
            inverseJoinColumns = @JoinColumn(name = "id_ap")
    )
    
    private List<Apartamento> apartments;
}
