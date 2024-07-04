package com.superradicado.radicados.auditoria.dto.mostrar;


import com.superradicado.radicados.auditoria.entidades.Auditoria;

import java.time.LocalDate;

public record MostrarAuditoriaDto(
        Long id,
        String usuario,
        LocalDate fechaTransaccion,
        String tipoTransaccion,
        String observacion,
        String nombreEquipo,
        String direccionIp) {
    public MostrarAuditoriaDto(Auditoria auditoria){
        this(auditoria.getId(),
                auditoria.getUsuario().getNombre(),
                auditoria.getFechaTransaccion(),
                auditoria.getTipoTransaccion(),
                auditoria.getObservacion(),
                auditoria.getNombreEquipo(),
                auditoria.getDireccionIp());
    }
}
