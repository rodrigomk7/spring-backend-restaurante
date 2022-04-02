package com.coderhouse.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Table(name = "restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String descripcion;
    @JsonProperty("hora_apertura")
    private String horaApertura;
    @JsonProperty("hora_cierre")
    private String horaCierre;
    private String telefono;
    private String latitud;
    private String longitud;
    private Boolean borrado;
    @JsonProperty("fecha_creacion")
    private Date fechaCreacion = Calendar.getInstance().getTime();
    @OneToMany(mappedBy = "id")
    private List<Categoria> categorias;
    @OneToOne(targetEntity=Ciudad.class, cascade = {CascadeType.ALL})
    private Ciudad ciudad;
}
