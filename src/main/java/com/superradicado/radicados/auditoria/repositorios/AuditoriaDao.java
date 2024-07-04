package com.superradicado.radicados.auditoria.repositorios;

import com.superradicado.radicados.auditoria.entidades.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AuditoriaDao extends JpaRepository<Auditoria,Long> {

    List<Auditoria> findByUsuarioId(Long usuarioId);
    List<Auditoria> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Auditoria> findByUsuarioIdAndFechaBetween(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin);
}
