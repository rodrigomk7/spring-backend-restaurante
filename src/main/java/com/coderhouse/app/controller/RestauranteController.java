package com.coderhouse.app.controller;

import com.coderhouse.app.handler.IdNotFoundException;
import com.coderhouse.app.model.Restaurante;
import com.coderhouse.app.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurante")
public class RestauranteController {

    private final RestauranteService service;

    //http://localhost:8080/openapi/swagger-ui/index.html

    @Operation(summary = "Obtener todos los restaurantes disponibles")
    @GetMapping("/all")
    public Iterable<Restaurante> readAll() {
        log.info("Request GET readAll()");
        return this.service.getAll();
    }

    @Operation(summary = "Obtener un restaurant a partir de un id")
    @GetMapping("/{id}")
    public Restaurante getById(@PathVariable Long id) throws IdNotFoundException  {
        log.info("Request GET getById:" + id);
        return this.service.getRestauranteById(id);
    }

    @Operation(summary = "Crear un restaurante")
    @PostMapping("")
    public Restaurante createRestaurante(@RequestBody Restaurante restaurante) {
        log.info("Request POST createRestaurante");
        return this.service.createRestaurante(restaurante);
    }

    @Operation(summary = "Actualizar un restaurante", description = "El restaurante enviado como body en el request " +
            "debe contener el id del restaurante a actualizar.")
    @PutMapping("")
    public void updateRestaurante(@RequestBody Restaurante restaurante) throws IdNotFoundException {
        log.info("Request PUT updateRestaurante");
        this.service.updateRestaurante(restaurante.getId(), restaurante);
    }

    @Operation(summary = "Eliminar un restaurante a partir de un id")
    @DeleteMapping("/{id}")
    public void deleteRestaurante(@PathVariable Long id) throws IdNotFoundException {
        log.info("Request DELETE deleteRestaurante id->:" + id);
        this.service.deleteRestaurante(id);
    }

    @Operation(deprecated = true)
    @PostMapping("/stringMap")
    public String createRestaurante(@RequestBody String restaurante) {
        return this.service.createRestauranteMap(restaurante);
    }
}
