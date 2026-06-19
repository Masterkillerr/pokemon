package com.pokemon.backend.controller;

import com.pokemon.backend.dto.PokemonResponse;
import com.pokemon.backend.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
