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

/**
 * Servicio para la gestion de entrenadores.
 * Proporciona operaciones de autenticacion y asignacion de pokemons a entrenadores.
 */
@Service
@RequiredArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonCapturaRepository pokemonCapturaRepository;

    /**
     * Autentica un entrenador por su email y devuelve su UUID.
     *
     * @param request Objeto con el email del entrenador
     * @return UUID del entrenador encontrado
     * @throws ResourceNotFoundException si no existe un entrenador con ese email
     */
    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        Entrenador entrenador = entrenadorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Entrenador no encontrado con email: " + request.getEmail()));
        return entrenador.getUuid();
    }

    /**
     * Obtiene la lista de pokemons capturados por un entrenador.
     *
     * @param uuid UUID del entrenador
     * @return Lista de PokemonResponse con los pokemons del entrenador
     * @throws ResourceNotFoundException si no existe un entrenador con ese UUID
     */
    @Transactional(readOnly = true)
    public List<PokemonResponse> getPokemonsByEntrenadorUuid(String uuid) {
        Entrenador entrenador = entrenadorRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Entrenador no encontrado con uuid: " + uuid));

        return entrenador.getCapturas().stream()
                .map(captura -> mapToPokemonResponse(captura.getPokemon()))
                .collect(Collectors.toList());
    }

    /**
     * Asigna un pokemon a un entrenador. Crea un registro de captura.
     * Valida que tanto el entrenador como el pokemon existan,
     * y que el pokemon no haya sido capturado previamente por el mismo entrenador.
     *
     * @param entrenadorUuid UUID del entrenador
     * @param pokemonUuid UUID del pokemon a asignar
     * @return Lista actualizada de PokemonResponse del entrenador
     * @throws ResourceNotFoundException si el entrenador o el pokemon no existen
     * @throws PokemonYaRegistradoException si el pokemon ya esta registrado al entrenador
     */
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
            throw new PokemonYaRegistradoException("Pokemon ya esta registrado al entrenador");
        }

        PokemonCapturaId capturaId = new PokemonCapturaId(pokemon.getId(), entrenador.getId());
        PokemonCaptura captura = new PokemonCaptura(capturaId, pokemon, entrenador);
        pokemonCapturaRepository.save(captura);

        return getPokemonsByEntrenadorUuid(entrenadorUuid);
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
