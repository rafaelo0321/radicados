package com.superradicado.radicados.usuario.repositorios;

import com.superradicado.radicados.usuario.entidades.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepositorio extends JpaRepository<Roles, Long> {

    Optional<Roles> findByNombre(String nombre);
}