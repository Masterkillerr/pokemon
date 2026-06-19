package com.pokemon.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tipo_pokemon", schema = "pokemon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String descripcion;

    @Column(nullable = false, unique = true, length = 100)
    private String uuid;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
