package com.example.upla.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Builder
@Table(name = "apartamento")
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

    @Column
    private Double precio;

    @ManyToMany(mappedBy = "apartments")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Administrador> administrators = new HashSet<>();

    @OneToMany(mappedBy = "apartamento")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Reserva> reservas = new HashSet<>();


    public void addAdministrador(Administrador administrador) {
        this.administrators.add(administrador);
        administrador.getApartments().add(this);
    }


}