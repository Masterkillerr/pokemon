package com.pokemon.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pokemonOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pokemon API")
                        .description("API REST para el sistema de gestion de Pokemon")
                        .version("1.0.0")
                        .license(new License().name("MIT")));
    }
}
