package com.coderhouse.app.repository;

import com.coderhouse.app.model.Restaurante;
import org.springframework.data.repository.CrudRepository;

public interface RestauranteRepository extends CrudRepository<Restaurante, Long> {
}
