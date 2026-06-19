package com.pokemon.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemons")
@RequiredArgsConstructor
@Tag(name = "Pokemon", description = "Endpoints para gestion de Pokemon")
public class PokemonController {
    // Services will be added incrementally
}
