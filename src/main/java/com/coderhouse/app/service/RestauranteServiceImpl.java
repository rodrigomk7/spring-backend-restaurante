package com.coderhouse.app.service;

import com.coderhouse.app.cache.CacheClient;
import com.coderhouse.app.handler.IdNotFoundException;
import com.coderhouse.app.model.Restaurante;
import com.coderhouse.app.repository.RestauranteRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository repository;
    private final ObjectMapper mapper;
    //redis
    private final CacheClient<Restaurante> cache;


    //config
    @PostConstruct
    private void PostConfig() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
    }


    @Override
    public Restaurante createRestaurante(Restaurante restaurante) {
        try {
            //Serializamos el restaurante a JSON
            mapperToString(restaurante);
            mapperToMap(restaurante);
            //repository
            var data = repository.save(restaurante);
            //redis
            return cache.save(restaurante.getId().toString(), restaurante);
        } catch (JsonProcessingException e) {
            log.error("Error converting restaurant to string", e);
        }
        return restaurante;
    }

    @Override
    public Iterable<Restaurante> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Restaurante getRestauranteById(Long id) throws IdNotFoundException {
        if (id == 0) {
            throw new IdNotFoundException();
        }

        var dataFromCache = cache.recover(id.toString(), Restaurante.class);

        //si encuentra el objeto en cache
        if (!Objects.isNull(dataFromCache)) {
            return dataFromCache;
        }

        //si no está en cache lo busca en la base de datos h2 (repository)
        //y se guarda en cache
        var dataFromDatabase = repository.findById(id).orElseThrow(() -> new IdNotFoundException());
        return cache.save(dataFromDatabase.getId().toString(), dataFromDatabase);
    }

    @Override
    public void updateRestaurante(Long id, Restaurante restaurante) throws IdNotFoundException {
        //para el update primero eliminamos y luego hacemos un create
        deleteRestaurante(id);
        createRestaurante(restaurante);
    }

    @Override
    public void deleteRestaurante(Long id) throws IdNotFoundException {
        if (id == 0) {
            throw new IdNotFoundException();
        }

        var restoData = getRestauranteById(id);
        //eliminamos del repository
        this.repository.delete(restoData);
        //eliminamos de la cache
        this.cache.delete(id.toString());
    }

    @Override
    public String createRestauranteMap(String restaurante)  {
        try {
            //deserealizado JSON->Objeto Restaurante
            var restauranteClass = mapper.readValue(restaurante, Restaurante.class);
            //almacenamos en repo
            Restaurante newRest = this.repository.save(restauranteClass);

            var restauranteString = mapper.writeValueAsString(newRest);
            var restauranteMap = mapper.readValue(restauranteString, Map.class);
            //devolver un restaurante serializado a String en formato Map
            return restauranteMap.toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return restaurante;
    }

    private void mapperToString(Restaurante restaurante) throws JsonProcessingException {
        var restauranteString = mapper.writeValueAsString(restaurante);
        log.info("Restaurante en formato String : {}", restaurante);
    }

    private void mapperToMap(Restaurante restaurante) throws JsonProcessingException {
        var restauranteString = mapper.writeValueAsString(restaurante);
        var restauranteMap = mapper.readValue(restauranteString, Map.class);
        log.info("Restaurante en formato de Mapa : {}", restauranteMap.toString());
    }


}
