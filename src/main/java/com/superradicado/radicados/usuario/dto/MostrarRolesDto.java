package com.superradicado.radicados.usuario.dto;

import com.superradicado.radicados.usuario.entidades.Roles;

import java.time.LocalDateTime;

public record MostrarRolesDto(String nombre,
                              LocalDateTime fechaDeCreacion,
                              LocalDateTime fechaActualizacion) {
    public MostrarRolesDto(Roles rol){
        this(
                rol.getNombre(),
                LocalDateTime.now(),
                rol.getFechaActualizacion());
    }
}
//Mirar usuarios con spring security