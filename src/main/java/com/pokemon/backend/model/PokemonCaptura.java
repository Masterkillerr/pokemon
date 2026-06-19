package com.pokemon.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pokemon_captura", schema = "pokemon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonCaptura {

    @EmbeddedId
    private PokemonCapturaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pokemonId")
    @JoinColumn(name = "pokemon_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "capturas"})
    private Pokemon pokemon;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("entrenadorId")
    @JoinColumn(name = "entrenador_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "capturas"})
    private Entrenador entrenador;
}
