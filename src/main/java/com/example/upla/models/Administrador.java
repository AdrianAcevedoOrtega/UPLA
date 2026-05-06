package com.example.upla.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Builder
@Table(name = "administradores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ad;

    @Column(nullable = false)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private Long n_ss;

    @ManyToMany
    @JoinTable(
            name = "administrator_apartment",
            joinColumns = @JoinColumn(name = "id_ad"),
            inverseJoinColumns = @JoinColumn(name = "id_ap")
    )
    private Set<Apartamento> apartments = new HashSet<>();

    public void addApartamento(Apartamento apartamento) {
        this.apartments.add(apartamento);
        apartamento.getAdministrators().add(this);
    }
}