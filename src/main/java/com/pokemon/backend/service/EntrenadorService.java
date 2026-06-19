package com.pokemon.backend.service;

import com.pokemon.backend.dto.LoginRequest;
import com.pokemon.backend.exception.ResourceNotFoundException;
import com.pokemon.backend.model.Entrenador;
import com.pokemon.backend.repository.EntrenadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        Entrenador entrenador = entrenadorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Entrenador no encontrado con email: " + request.getEmail()));
        return entrenador.getUuid();
    }
}
