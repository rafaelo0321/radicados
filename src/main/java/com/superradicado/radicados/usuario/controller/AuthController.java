package com.superradicado.radicados.usuario.controller;

import com.superradicado.radicados.auditoria.servicios.AuditoriaService;
import com.superradicado.radicados.usuario.dto.LoginDto;
import com.superradicado.radicados.usuario.dto.UsuarioDto;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import com.superradicado.radicados.usuario.servicios.IServiciosUsuarioAuth;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/autenticacion")
public class AuthController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final IServiciosUsuarioAuth iServiciosUsuario;
    private final AuditoriaService auditoria;

    public AuthController(IServiciosUsuarioAuth iServiciosUsuarioAuth, AuditoriaService auditoria) {
        this.iServiciosUsuario = iServiciosUsuarioAuth;
        this.auditoria = auditoria;
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> autenticarUsuario(
            @RequestBody @Valid LoginDto datosAutenticacionUsuario) {
        try{

            HashMap<String, String> login = iServiciosUsuario.login(datosAutenticacionUsuario);
            //auditoria.crearAuditoria("El usuario generó decarga de un archivo de excel con los radicados que se generaron en el día", HttpMethod.GET.toString(),authentication);

            if (login.containsKey("jwt")) {
                return new ResponseEntity<>(iServiciosUsuario.login(datosAutenticacionUsuario), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(iServiciosUsuario.login(datosAutenticacionUsuario), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(
            @RequestBody UsuarioDto datos
    ){
        try {
            return iServiciosUsuario.registrar(datos);
        }catch (Exception exception){
            LOGGER.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
