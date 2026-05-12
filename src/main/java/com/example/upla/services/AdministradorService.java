package com.example.upla.services;

import com.example.upla.models.Administrador;
import com.example.upla.models.Apartamento;
import com.example.upla.repositories.AdministradorRepository;
import com.example.upla.repositories.ApartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministradorService {
    // Atributos
    private final ApartamentoRepository apartamentoRepository;
    private final AdministradorRepository administradorRepository;

    // Métodos
    public void asignarAdministradorAApartamento(Long idApartamento, Long idAdministrador) {
        // Lógica para asignar un administrador a un apartamento
        Apartamento apartamento = apartamentoRepository.findById(idApartamento).orElseThrow(() -> new RuntimeException("Error: El apartamento con ID " + idApartamento + " no existe."));

        Administrador admin = administradorRepository.findById(idAdministrador).orElseThrow(() -> new RuntimeException("Error: El administrador con ID " + idAdministrador + " no existe."));

        apartamento.addAdministrador(admin);
        apartamentoRepository.save(apartamento);
    }
}
