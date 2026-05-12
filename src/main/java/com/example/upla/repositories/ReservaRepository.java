package com.example.upla.repositories;

import com.example.upla.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ReservaRepository extends JpaRepository<Reserva, String> {
    @Query("select case when count (r) > 0 then true else false end " +
            "from Reserva r where r.apartamento.id_ap = :idApartamento " +
            "and (:nuevaEntrada < r.f_salida and :nuevaSalida > r.f_entrada)")
    boolean existeSolapamiento(
            @Param("idApartamento")Long idApartamento,
            @Param("nuevaEntrada") Date nuevaEntrada,
            @Param("nuevaSalida") Date nuevaSalida
    );
}
