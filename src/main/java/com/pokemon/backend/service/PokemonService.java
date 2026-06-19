package com.pokemon.backend.service;

import com.pokemon.backend.dto.PokemonRequest;
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

/**
 * Servicio para la gestion de Pokemon.
 * Proporciona operaciones de consulta y creacion de pokemons.
 */
@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TipoPokemonRepository tipoPokemonRepository;

    /**
     * Obtiene una lista de pokemons filtrados por su tipo (descripcion).
     * La busqueda no distingue entre mayusculas y minusculas.
     *
     * @param tipo Descripcion del tipo de pokemon (ej: "agua", "fuego")
     * @return Lista de PokemonResponse con los pokemons del tipo especificado
     * @throws ResourceNotFoundException si no existe el tipo de pokemon
     */
    @Transactional(readOnly = true)
    public List<PokemonResponse> getPokemonsByTipo(String tipo) {
        TipoPokemon tipoPokemon = tipoPokemonRepository.findByDescripcionIgnoreCase(tipo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de pokemon no encontrado: " + tipo));

        return pokemonRepository.findByTipoPokemon(tipoPokemon).stream()
                .map(this::mapToPokemonResponse)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo pokemon en el sistema.
     * Valida que el tipo de pokemon exista antes de crearlo.
     *
     * @param request Datos del pokemon a crear (nombre, descripcion, tipo, generacion, etc.)
     * @return PokemonResponse con los datos del pokemon creado
     * @throws ResourceNotFoundException si el tipo de pokemon especificado no existe
     */
    @Transactional
    public PokemonResponse createPokemon(PokemonRequest request) {
        TipoPokemon tipoPokemon = tipoPokemonRepository.findById(request.getTipoPokemonId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de pokemon no encontrado con id: " + request.getTipoPokemonId()));

        Pokemon pokemon = new Pokemon();
        pokemon.setNombre(request.getNombre());
        pokemon.setDescripcion(request.getDescripcion());
        pokemon.setFechaDescubrimiento(request.getFechaDescubrimiento());
        pokemon.setTipoPokemon(tipoPokemon);
        pokemon.setGeneracion(request.getGeneracion());

        pokemon = pokemonRepository.save(pokemon);
        return mapToPokemonResponse(pokemon);
    }

    /**
     * Convierte una entidad Pokemon a un DTO PokemonResponse.
     *
     * @param pokemon Entidad Pokemon a mapear
     * @return PokemonResponse con los datos formateados para la API
     */
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
