package com.pokemon.backend.repository;

import com.pokemon.backend.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {
    Optional<Entrenador> findByEmail(String email);
    Optional<Entrenador> findByUuid(String uuid);
    boolean existsByEmail(String email);
}
