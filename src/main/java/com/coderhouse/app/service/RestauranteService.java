package com.coderhouse.app.service;

import com.coderhouse.app.handler.IdNotFoundException;
import com.coderhouse.app.model.Restaurante;

import java.util.List;

public interface RestauranteService {
    public Restaurante createRestaurante(Restaurante restaurante);
    public Iterable<Restaurante> getAll();

    public Restaurante getRestauranteById (Long id) throws IdNotFoundException;
    public void updateRestaurante(Long id, Restaurante restaurante) throws IdNotFoundException ;
    public void deleteRestaurante(Long id) throws IdNotFoundException ;

    public String createRestauranteMap(String restaurante);
}
