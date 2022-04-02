package com.coderhouse.app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI config() {
        return new OpenAPI()
                .components(new Components())
                .info(infoConfig());
    }

    private Info infoConfig() {
        return new Info()
                .version("1.0.0")
                .title("Restaurante API")
                .description("Desafío CoderHouse. " +
                        "Este servicio es un ejemplo de usar OpenAPI en Spring Boot");
    }
}
