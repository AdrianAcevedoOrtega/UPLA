package com.example.upla.repositories;

import com.example.upla.models.Apartamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartamentoRepository extends JpaRepository<Apartamento, Long> {
}
