package com.pokemon.backend.controller;

import com.pokemon.backend.dto.LoginRequest;
import com.pokemon.backend.dto.LoginResponse;
import com.pokemon.backend.dto.PokemonResponse;
import com.pokemon.backend.service.EntrenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/entrenador")
@RequiredArgsConstructor
@Tag(name = "Entrenador", description = "Endpoints para gestion de entrenadores Pokemon")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    @PostMapping("/login")
    @Operation(summary = "Obtener UUID de un entrenador por su email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UUID encontrado"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String uuid = entrenadorService.login(request);
        return ResponseEntity.ok(LoginResponse.builder().uuid(uuid).build());
    }

    @GetMapping("/{uuid}/pokemons")
    @Operation(summary = "Listar pokemons de un entrenador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pokemons del entrenador"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PokemonResponse>> getPokemonsByEntrenador(
            @Parameter(description = "UUID del entrenador", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
            @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Formato de UUID invalido")
            @PathVariable String uuid) {
        List<PokemonResponse> pokemons = entrenadorService.getPokemonsByEntrenadorUuid(uuid);
        return ResponseEntity.ok(pokemons);
    }

    @PostMapping("/{uuid}/pokemons/{pokemonUuid}")
    @Operation(summary = "Agregar un pokemon al listado de pokemons de un entrenador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pokemon agregado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Entrenador o pokemon no encontrado"),
            @ApiResponse(responseCode = "409", description = "Pokemon ya registrado al entrenador"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PokemonResponse>> addPokemonToEntrenador(
            @Parameter(description = "UUID del entrenador", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
            @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Formato de UUID invalido")
            @PathVariable String uuid,
            @Parameter(description = "UUID del pokemon", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
            @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Formato de UUID invalido")
            @PathVariable String pokemonUuid) {
        List<PokemonResponse> pokemons = entrenadorService.addPokemonToEntrenador(uuid, pokemonUuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemons);
    }
}
