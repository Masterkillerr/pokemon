package com.pokemon.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entrenador")
@RequiredArgsConstructor
@Tag(name = "Entrenador", description = "Endpoints para gestion de entrenadores Pokemon")
public class EntrenadorController {
    // Services will be added incrementally
}
