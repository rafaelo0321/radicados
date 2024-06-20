package com.superradicado.radicados.usuario.entidades;

import com.superradicado.radicados.usuario.dto.MostrarRolesDto;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Entity
@Table(name = "TB_ROLES")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, name = "NOMBRE")
    private String nombre;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "CREATED_AT")
    private LocalDateTime fechaDeCreacion;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "UPDATED_AT")
    private LocalDateTime fechaActualizacion;

    public Roles() {
    }

    public Roles(String nombre,
                LocalDateTime fechaDeCreacion,
                LocalDateTime fechaActualizacion) {
        this.nombre = nombre;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Roles(MostrarRolesDto dto) {
        this.nombre = dto.nombre();
        this.fechaDeCreacion = dto.fechaDeCreacion();
        this.fechaActualizacion = dto.fechaActualizacion();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
