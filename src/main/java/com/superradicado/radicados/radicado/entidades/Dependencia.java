package com.superradicado.radicados.radicado.entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Dependencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroDependencia;
    private String nombre;
    @OneToMany(fetch = FetchType.EAGER,
            targetEntity = Radicado.class,
            mappedBy = "dependencia",
            cascade = CascadeType.ALL)
    private List<Radicado> radicados = new ArrayList<>();

    public Dependencia(Integer numeroDependencia, String nombre) {
        this.numeroDependencia = numeroDependencia;
        this.nombre = nombre;
    }

    public Dependencia() {
    }

    public Long getId() {
        return id;
    }

    public Integer getNumeroDependencia() {
        return numeroDependencia;
    }

    public void setNumeroDependencia(Integer numeroDependencia) {
        this.numeroDependencia = numeroDependencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Radicado> getRadicados() {
        return radicados;
    }

    public void setRadicados(List<Radicado> radicados) {
        this.radicados = radicados;
    }
}
