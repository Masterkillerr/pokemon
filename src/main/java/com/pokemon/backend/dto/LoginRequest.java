package com.pokemon.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body para login de entrenador")
public class LoginRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email invalido")
    @Schema(description = "Email del entrenador", example = "ash@gmail.com")
    private String email;
}
