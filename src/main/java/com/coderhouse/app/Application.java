package com.coderhouse.app;

import com.coderhouse.app.model.Categoria;
import com.coderhouse.app.model.Ciudad;
import com.coderhouse.app.model.Pais;
import com.coderhouse.app.model.Restaurante;
import com.coderhouse.app.repository.RestauranteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(RestauranteRepository repository) {
		return (args) -> {
			repository.save(
					Restaurante.builder()
							.nombre("RestoName1")
							.descripcion("resto1")
							.horaCierre("08:00")
							.longitud("123.12")
							.latitud("123.12")
							.ciudad(Ciudad.builder().
									nombre("NombreCiudad")
									.pais(Pais.builder().nombre("NombrePais").build())
									.build())
							.build()
			);
		};
	}
}