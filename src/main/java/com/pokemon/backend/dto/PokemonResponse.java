package com.pokemon.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta con datos de un pokemon")
public class PokemonResponse {

    @Schema(description = "ID del pokemon", example = "1")
    private Integer id;

    @Schema(description = "Nombre del pokemon", example = "bulbasaur")
    private String nombre;

    @Schema(description = "Descripcion del pokemon", example = "planta")
    private String descripcion;

    @Schema(description = "Tipo de pokemon")
    private TipoPokemonDTO tipoPokemon;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Fecha de descubrimiento", example = "2024-01-03")
    private LocalDate fechaDescubrimiento;

    @Schema(description = "Generacion del pokemon", example = "1")
    private Integer generacion;

    @Schema(description = "UUID del pokemon", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private String uuid;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Informacion del tipo de pokemon")
    public static class TipoPokemonDTO {

        @Schema(description = "ID del tipo", example = "1")
        private Integer id;

        @Schema(description = "Descripcion del tipo", example = "agua")
        private String descripcion;

        @Schema(description = "UUID del tipo", example = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        private String uuid;
    }
}
