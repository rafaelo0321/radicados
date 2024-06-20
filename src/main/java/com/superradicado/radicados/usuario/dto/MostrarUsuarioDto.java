package com.superradicado.radicados.usuario.dto;

import com.superradicado.radicados.usuario.entidades.Usuario;

import java.util.Set;
import java.util.stream.Collectors;

public record MostrarUsuarioDto(Long id, String nombre, String correo,boolean estado , Set<MostrarRolesDto> idRol) {
    public MostrarUsuarioDto(Usuario dto){
        this(dto.getId(),
                dto.getNombre(),
                dto.getCorreo(),
                dto.getEstado(),
                dto.getRoles().stream().map(MostrarRolesDto::new).collect(Collectors.toSet())
        );
    }
}
