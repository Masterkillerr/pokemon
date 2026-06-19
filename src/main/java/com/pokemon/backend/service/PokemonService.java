package com.pokemon.backend.service;

import com.pokemon.backend.dto.PokemonResponse;
import com.pokemon.backend.exception.ResourceNotFoundException;
import com.pokemon.backend.model.Pokemon;
import com.pokemon.backend.model.TipoPokemon;
import com.pokemon.backend.repository.PokemonRepository;
import com.pokemon.backend.repository.TipoPokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TipoPokemonRepository tipoPokemonRepository;

    @Transactional(readOnly = true)
    public List<PokemonResponse> getPokemonsByTipo(String tipo) {
        TipoPokemon tipoPokemon = tipoPokemonRepository.findByDescripcionIgnoreCase(tipo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de pokemon no encontrado: " + tipo));

        return pokemonRepository.findByTipoPokemon(tipoPokemon).stream()
                .map(this::mapToPokemonResponse)
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
