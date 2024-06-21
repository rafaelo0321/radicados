package com.superradicado.radicados.usuario.repositorios;

import com.superradicado.radicados.usuario.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepositorio extends JpaRepository <Usuario,Long>{

   List<Usuario> findByEstadoTrue();
   List<Usuario> findByEstadoFalse();
   Optional<Usuario> findByNombre(String nombre);
   Optional<Usuario> findByCorreo(String correo);


}