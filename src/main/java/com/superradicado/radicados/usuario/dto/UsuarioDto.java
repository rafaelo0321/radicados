package com.superradicado.radicados.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(
        @NotBlank(message = "El nombre no debe estar vacio")
        String nombre,

        String contrasenha,
        String correo,
        Long idRol) {
}
