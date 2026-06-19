package com.pokemon.backend.repository;

import com.pokemon.backend.model.TipoPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoPokemonRepository extends JpaRepository<TipoPokemon, Integer> {
    Optional<TipoPokemon> findByDescripcionIgnoreCase(String descripcion);
}
