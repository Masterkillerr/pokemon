package com.pokemon.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Respuesta con UUID del entrenador")
public class LoginResponse {

    @Schema(description = "UUID del entrenador", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private String uuid;
}
