package com.pokemon.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pokemon", schema = "pokemon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_pokemon", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TipoPokemon tipoPokemon;

    @Column(name = "fecha_descubrimiento", nullable = false)
    private LocalDate fechaDescubrimiento;

    @Column(nullable = false)
    private Integer generacion;

    @Column(nullable = false, unique = true, length = 100)
    private String uuid;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"pokemon", "entrenador"})
    private List<PokemonCaptura> capturas = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        if (generacion == null) {
            generacion = 1;
        }
    }
}
