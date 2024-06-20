package com.superradicado.radicados.usuario.servicios.implementaciones;


import com.superradicado.radicados.usuario.dto.MostrarUsuarioDto;
import com.superradicado.radicados.usuario.dto.UsuarioDto;
import com.superradicado.radicados.usuario.entidades.Usuario;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import com.superradicado.radicados.usuario.servicios.IServiciosDeUsuario;
import com.superradicado.radicados.utilitario.ErrorDto;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Random;

@Service
public class ImplementacionDeServiciosDeUsuario implements IServiciosDeUsuario {


    private static final Logger LOG = LoggerFactory.getLogger(ImplementacionDeServiciosDeUsuario.class);
    private final IUsuarioRepositorio iUsuarioRepositorio;

    public ImplementacionDeServiciosDeUsuario(IUsuarioRepositorio iUsuarioRepositorio) {
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }


    @Override
    public ResponseEntity<?> editarUsuario(Long id, UsuarioDto dto, Authentication authentication){

        Usuario usuario = iUsuarioRepositorio.findById(id).orElse(null);
        try{
            usuario.actualizarUsuario(dto);
            iUsuarioRepositorio.save(usuario);
            return new ResponseEntity<>(new MostrarUsuarioDto(usuario), HttpStatus.OK);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> mostrarUsuario(Long id){
        try{
            Usuario usuario = iUsuarioRepositorio.findById(id).orElse(null);
            iUsuarioRepositorio.save(usuario);
            return new ResponseEntity<>(new MostrarUsuarioDto(usuario), HttpStatus.OK);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<List<?>> mostrarUsuarios() {
        try{
            List<MostrarUsuarioDto> mostrar = iUsuarioRepositorio.findAll().stream().map(MostrarUsuarioDto::new).collect(Collectors.toList());
            return new ResponseEntity<>(mostrar,HttpStatus.OK);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<?> desactivarUsuario(Long id, Authentication authentication){
        try {

            Usuario admin = iUsuarioRepositorio.findByNombre(authentication.getName()).orElse(null);
            Usuario aDesactivar = iUsuarioRepositorio.findById(id).orElse(null);

            if (Objects.equals(admin.getNombre(), Objects.requireNonNull(aDesactivar).getNombre())){
                LOG.info("Un usuario no se puede autodesactivar o desactivar a si mismo");
                return new ResponseEntity<>(new ErrorDto("Un usuario no se puede autodesactivar o desactivar a si mismo"), HttpStatus.NOT_ACCEPTABLE);
            }
            if (admin.getEstado()){
                LOG.info("Un usuario desactivado no puede desactivar usuarios");
                return new ResponseEntity<>(new ErrorDto("Un usuario desactivado no puede desactivar usuarios"), HttpStatus.NOT_ACCEPTABLE);
            }

            if (admin.getRoles() != null ) {
                Usuario usuario = iUsuarioRepositorio.findById(id).orElse(null);
                if (usuario != null) {
                    usuario.darDebaja();
                    iUsuarioRepositorio.save(usuario);
                    return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
                } else {
                    LOG.info("No se encontró al usuario a desactivar");
                    return new ResponseEntity<>(new ErrorDto("No se encontró al usuario a desactivar"), HttpStatus.NOT_FOUND);
                }
            } else {

                LOG.info("El usuario no cuenta con los permisos para realizar esta accion");

                return new ResponseEntity<>(new ErrorDto("No tiene permisos para realizar esta acción"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
