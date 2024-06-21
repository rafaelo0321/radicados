package com.superradicado.radicados.radicado.repositorios;

import com.superradicado.radicados.radicado.entidades.Radicado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRadicadoRepositorio extends JpaRepository<Radicado,Long> {
}
