package com.superradicado.radicados.radicado.repositorios;

import com.superradicado.radicados.radicado.entidades.Radicado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IRadicadoRepositorio extends JpaRepository<Radicado,Long> {
    Radicado findByNumeroRadicado(String numero);
    List<Radicado> findByFechaCreacionBetween(LocalDateTime n,LocalDateTime ahora);
    List<Radicado> findByDependenciaNumeroDependencia(Integer numero);
}
