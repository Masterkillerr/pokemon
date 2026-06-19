package com.pokemon.backend.controller;

import com.pokemon.backend.dto.LoginRequest;
import com.pokemon.backend.dto.LoginResponse;
import com.pokemon.backend.service.EntrenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
