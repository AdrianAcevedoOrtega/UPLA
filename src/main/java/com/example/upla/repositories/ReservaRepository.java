package com.example.upla.repositories;

import com.example.upla.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String> {

    /**
     * Standard Overlap Check: Used when creating a new reservation.
     */
    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.habitacionTipo = :aptoId " +
            "AND r.status != 'CANCELLED' " +
            "AND (:inicio < r.fechaSalida AND :fin > r.fechaEntrada)")
    boolean existsOverlappingReserva(
            @Param("aptoId") String aptoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    /**
     * Update Overlap Check: Used when editing an existing reservation.
     * It ignores the current reservation ID so it doesn't collide with itself.
     */
    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.habitacionTipo = :aptoId " +
            "AND r.id != :currentId " +
            "AND r.status != 'CANCELLED' " +
            "AND (:inicio < r.fechaSalida AND :fin > r.fechaEntrada)")
    boolean existsOverlappingReservaExcludingSelf(
            @Param("aptoId") String aptoId,
            @Param("currentId") String currentId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}
