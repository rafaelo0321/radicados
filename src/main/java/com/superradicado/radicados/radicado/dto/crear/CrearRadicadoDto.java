package com.superradicado.radicados.radicado.dto.crear;

import java.time.LocalDateTime;

public record CrearRadicadoDto(Long id,
                               String nombreEmpresa,
                               String personaQueRadica,
                               String asunto,
                               String descripcion,
                               Integer folio,
                               Integer anexos) {
}
