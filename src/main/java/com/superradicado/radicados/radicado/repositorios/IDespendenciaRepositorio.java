package com.superradicado.radicados.radicado.repositorios;

import com.superradicado.radicados.radicado.entidades.Dependencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDespendenciaRepositorio extends JpaRepository<Dependencia,Long> {
    Dependencia findByNombre(String nombre);
}