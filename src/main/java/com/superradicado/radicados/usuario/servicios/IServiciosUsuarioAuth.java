package com.superradicado.radicados.usuario.servicios;

import com.superradicado.radicados.usuario.dto.LoginDto;
import com.superradicado.radicados.usuario.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface IServiciosUsuarioAuth {
    ResponseEntity<?> registrar(UsuarioDto usuarioDto);
    HashMap<String, String> login(LoginDto loginDto);
}
