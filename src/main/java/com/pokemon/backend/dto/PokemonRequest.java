package com.pokemon.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Request body para registrar un nuevo pokemon")
public class PokemonRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del pokemon", example = "programador")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Schema(description = "Descripcion del pokemon", example = "desarrollador full")
    private String descripcion;

    @NotNull(message = "La fecha de descubrimiento es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Fecha de descubrimiento", example = "2024-01-03")
    private LocalDate fechaDescubrimiento;

    @NotNull(message = "El tipo de pokemon es obligatorio")
    @Schema(description = "ID del tipo de pokemon", example = "1")
    private Integer tipoPokemonId;

    @Schema(description = "Generacion del pokemon", example = "1")
    @NotNull(message = "La generacion es obligatoria")
    private Integer generacion;
}
