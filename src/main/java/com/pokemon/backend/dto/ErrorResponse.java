package com.pokemon.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Respuesta de error")
public class ErrorResponse {

    @Schema(description = "Indica si hubo error", example = "true")
    private boolean error;

    @Schema(description = "Mensaje de error", example = "pokemon ya esta registrado al entrenador")
    private String message;
}
