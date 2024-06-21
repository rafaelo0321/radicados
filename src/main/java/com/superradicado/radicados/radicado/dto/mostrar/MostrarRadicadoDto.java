package com.superradicado.radicados.radicado.dto.mostrar;

import com.superradicado.radicados.radicado.entidades.Radicado;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record MostrarRadicadoDto(Long id,
                                 String numeroRadicado,
                                 String nombreEmpresa,
                                 String personaQueRadica,
                                 String asunto,
                                 String descripcion,
                                 Integer folio,
                                 Integer anexos,
                                 LocalDateTime fechaCreacion) {
    public MostrarRadicadoDto(Radicado radicado){
        this(radicado.getId(),
                radicado.getNumeroRadicado(),
                radicado.getNombreEmpresa(),
                radicado.getPersonaQueRadica(),
                radicado.getAsunto(),
                radicado.getDescripcion(),
                radicado.getFolio(),
                radicado.getAnexos(),
                radicado.getFechaCreacion());
    }
}
