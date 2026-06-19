package com.pokemon.backend.service;

import com.pokemon.backend.dto.LoginRequest;
import com.pokemon.backend.dto.PokemonResponse;
import com.pokemon.backend.exception.ResourceNotFoundException;
import com.pokemon.backend.model.Entrenador;
import com.pokemon.backend.model.Pokemon;
import com.pokemon.backend.repository.EntrenadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<PokemonResponse> getPokemonsByEntrenadorUuid(String uuid) {
        Entrenador entrenador = entrenadorRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Entrenador no encontrado con uuid: " + uuid));

        return entrenador.getCapturas().stream()
                .map(captura -> mapToPokemonResponse(captura.getPokemon()))
                .collect(Collectors.toList());
    }

    private PokemonResponse mapToPokemonResponse(Pokemon pokemon) {
        return PokemonResponse.builder()
                .id(pokemon.getId())
                .nombre(pokemon.getNombre())
                .descripcion(pokemon.getDescripcion())
                .tipoPokemon(PokemonResponse.TipoPokemonDTO.builder()
                        .id(pokemon.getTipoPokemon().getId())
                        .descripcion(pokemon.getTipoPokemon().getDescripcion())
                        .uuid(pokemon.getTipoPokemon().getUuid())
                        .build())
                .fechaDescubrimiento(pokemon.getFechaDescubrimiento())
                .generacion(pokemon.getGeneracion())
                .uuid(pokemon.getUuid())
                .build();
    }
}
