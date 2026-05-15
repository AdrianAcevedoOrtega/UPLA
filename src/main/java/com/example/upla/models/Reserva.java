package com.example.upla.models;

import com.example.upla.models.enums.ReservaStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    private String id;

    @Column(nullable = false)
    private String clienteNombre;

    @Column(nullable = false)
    private String habitacionTipo;

    @Column(nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(nullable = false)
    private LocalDateTime fechaSalida;

    @Enumerated(EnumType.STRING)
    private ReservaStatus status;

    @Column(nullable = false)
    private Double precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ap")
    private Apartamento apartamento;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString().substring(0, 8);
        }
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ReservaStatus.PENDING;
        }
    }
}