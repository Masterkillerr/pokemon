package com.pokemon.backend.controller;

import com.pokemon.backend.dto.PokemonRequest;
import com.pokemon.backend.dto.PokemonResponse;
import com.pokemon.backend.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/pokemons")
@RequiredArgsConstructor
@Tag(name = "Pokemon", description = "Endpoints para gestion de Pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/{tipo}")
    @Operation(summary = "Listar pokemons de un tipo registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pokemons del tipo"),
            @ApiResponse(responseCode = "404", description = "Tipo de pokemon no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PokemonResponse>> getPokemonsByTipo(
            @Parameter(description = "Nombre del tipo de pokemon", example = "agua")
            @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El tipo debe contener solo letras")
            @PathVariable String tipo) {
        List<PokemonResponse> pokemons = pokemonService.getPokemonsByTipo(tipo);
        return ResponseEntity.ok(pokemons);
    }

    @PostMapping
    @Operation(summary = "Registrar un pokemon nuevo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pokemon creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion"),
            @ApiResponse(responseCode = "404", description = "Tipo de pokemon no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PokemonResponse> createPokemon(
            @Valid @RequestBody PokemonRequest request) {
        PokemonResponse response = pokemonService.createPokemon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
