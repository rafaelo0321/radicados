package com.superradicado.radicados.radicado.dto.crear;

import com.superradicado.radicados.radicado.enums.Contingencia;
import com.superradicado.radicados.radicado.enums.TipoDocumental;

import java.time.LocalDateTime;

public record CrearRadicadoDto(
                               String nombreEmpresa,
                               String personaQueRadica,
                               String asunto,
                               String descripcion,
                               int folio,
                               int anexos,
                               String nombreDependencia,
                               TipoDocumental tipoDocumental,
                               Contingencia contingencia) {
}
