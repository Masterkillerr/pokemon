package com.pokemon.backend.repository;

import com.pokemon.backend.model.Pokemon;
import com.pokemon.backend.model.TipoPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Optional<Pokemon> findByUuid(String uuid);
    List<Pokemon> findByTipoPokemon(TipoPokemon tipoPokemon);
}
