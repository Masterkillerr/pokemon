package com.pokemon.backend.repository;

import com.pokemon.backend.model.Entrenador;
import com.pokemon.backend.model.Pokemon;
import com.pokemon.backend.model.PokemonCaptura;
import com.pokemon.backend.model.PokemonCapturaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonCapturaRepository extends JpaRepository<PokemonCaptura, PokemonCapturaId> {
    List<PokemonCaptura> findByEntrenador(Entrenador entrenador);
    boolean existsByPokemonAndEntrenador(Pokemon pokemon, Entrenador entrenador);
}
