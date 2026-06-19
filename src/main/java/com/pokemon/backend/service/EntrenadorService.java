package com.pokemon.backend.service;

import com.pokemon.backend.dto.LoginRequest;
import com.pokemon.backend.dto.PokemonResponse;
import com.pokemon.backend.exception.PokemonYaRegistradoException;
import com.pokemon.backend.exception.ResourceNotFoundException;
import com.pokemon.backend.model.Entrenador;
import com.pokemon.backend.model.Pokemon;
import com.pokemon.backend.model.PokemonCaptura;
import com.pokemon.backend.model.PokemonCapturaId;
import com.pokemon.backend.repository.EntrenadorRepository;
import com.pokemon.backend.repository.PokemonCapturaRepository;
import com.pokemon.backend.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonCapturaRepository pokemonCapturaRepository;

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

    @Transactional
    public List<PokemonResponse> addPokemonToEntrenador(String entrenadorUuid, String pokemonUuid) {
        Entrenador entrenador = entrenadorRepository.findByUuid(entrenadorUuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Entrenador no encontrado con uuid: " + entrenadorUuid));

        Pokemon pokemon = pokemonRepository.findByUuid(pokemonUuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pokemon no encontrado con uuid: " + pokemonUuid));

        boolean yaRegistrado = pokemonCapturaRepository
                .existsByPokemonAndEntrenador(pokemon, entrenador);
        if (yaRegistrado) {
            throw new PokemonYaRegistradoException("pokemon ya esta registrado al entrenador");
        }

        PokemonCapturaId capturaId = new PokemonCapturaId(pokemon.getId(), entrenador.getId());
        PokemonCaptura captura = new PokemonCaptura(capturaId, pokemon, entrenador);
        pokemonCapturaRepository.save(captura);

        return getPokemonsByEntrenadorUuid(entrenadorUuid);
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
