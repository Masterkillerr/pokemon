package com.pokemon.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonCapturaId implements Serializable {

    @Column(name = "pokemon_id")
    private Integer pokemonId;

    @Column(name = "entrenador_id")
    private Integer entrenadorId;
}
