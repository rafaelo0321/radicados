package com.superradicado.radicados.usuario.dto;

import jakarta.validation.constraints.*;

public record LoginDto(
        String nombre,
        @NotEmpty(message = "The password cannot be empty")
        @NotBlank(message = "The password cannot be whitespaces")
        @NotBlank
        String clave
) {
}
