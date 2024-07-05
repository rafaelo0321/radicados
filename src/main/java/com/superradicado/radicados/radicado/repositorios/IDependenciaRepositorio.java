package com.superradicado.radicados.radicado.repositorios;

import com.superradicado.radicados.radicado.entidades.Dependencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDependenciaRepositorio extends JpaRepository<Dependencia,Long> {
    Dependencia findByNombre(String nombre);
}