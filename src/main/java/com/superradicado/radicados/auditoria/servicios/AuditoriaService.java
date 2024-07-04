package com.superradicado.radicados.auditoria.servicios;

import com.superradicado.radicados.auditoria.dto.mostrar.MostrarAuditoriaDto;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface AuditoriaService {
    MostrarAuditoriaDto mostrarAuditoriaId(Long id, Authentication authentication);
    List<MostrarAuditoriaDto> getMostrarAuditoria();
    List<MostrarAuditoriaDto> mostrarAuditoriaPorIdUsuario(Long id);
    void crearAuditoria(String observacion, String tipoTransaccion, Authentication user);
    List<MostrarAuditoriaDto> obtenerAuditoriasPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
    List<MostrarAuditoriaDto> obtenerAuditoriasPorFechaYUsuario(Long usuarioId,LocalDate fechaInicio, LocalDate fechaFin);
}
