package com.superradicado.radicados.radicado.dto.crear;

import com.superradicado.radicados.radicado.enums.Contingencia;
import com.superradicado.radicados.radicado.enums.TipoDocumental;

public record CrearRadicadoPresencialDto(
        String nombreEmpresa,
        int folio,
        int anexos,
        String nombreDependencia,
        TipoDocumental tipoDocumental,
        Contingencia contingencia) {
}