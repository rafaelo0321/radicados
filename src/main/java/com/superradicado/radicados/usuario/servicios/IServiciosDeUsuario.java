package com.superradicado.radicados.usuario.servicios;

import com.superradicado.radicados.usuario.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IServiciosDeUsuario {

    ResponseEntity<?> editarUsuario(Long id, UsuarioDto dto, Authentication authentication);
    ResponseEntity<?> mostrarUsuario(Long id);
    ResponseEntity<List<?>> mostrarUsuarios();
    ResponseEntity<?> desactivarUsuario(Long id,Authentication authentication);

}
