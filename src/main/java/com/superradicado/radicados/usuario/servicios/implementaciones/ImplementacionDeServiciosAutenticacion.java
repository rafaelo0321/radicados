package com.superradicado.radicados.usuario.servicios.implementaciones;

import com.superradicado.radicados.usuario.dto.*;
import com.superradicado.radicados.usuario.entidades.Roles;
import com.superradicado.radicados.usuario.entidades.Usuario;
import com.superradicado.radicados.usuario.repositorios.IRoleRepositorio;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import com.superradicado.radicados.usuario.servicios.IServiciosUsuarioAuth;
import com.superradicado.radicados.seguridad.servicios.IJWTUtilityService;
import com.superradicado.radicados.seguridad.validaciones.ValidacionesDeUsuarios;
import com.superradicado.radicados.utilitario.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImplementacionDeServiciosAutenticacion implements IServiciosUsuarioAuth {

    private static final Logger log = LoggerFactory.getLogger(ImplementacionDeServiciosAutenticacion.class);
    private final IUsuarioRepositorio userRepository;
    private final IRoleRepositorio rolRepository;
    private final IJWTUtilityService jwtUtilityService;
    private final ValidacionesDeUsuarios userValidations;
    private final PasswordEncoder passwordEncoder;

    public ImplementacionDeServiciosAutenticacion(IUsuarioRepositorio userRepository, IRoleRepositorio rolRepository, IJWTUtilityService jwtUtilityService, ValidacionesDeUsuarios userValidations, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.jwtUtilityService = jwtUtilityService;
        this.userValidations = userValidations;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public HashMap<String, String> login(LoginDto loginRequest) {
        try {

            HashMap<String, String> jwt = new HashMap<>();
            Optional<Usuario> userEntity = userRepository.findByNombre(loginRequest.nombre());

            if(userEntity.isPresent() && userEntity.get().getEstado()){
                jwt.put("error", "Acceso denegado, su usuario está bloqueado, contáctese con el administrador");
                return jwt;
            }

            if (userEntity.isEmpty()) {
                jwt.put("error", "Usuario no registrado!");
                return jwt;
            }
            if (verificarContrasena(loginRequest.clave(), userEntity.get().getClave())) {

                jwt.put("jwt", jwtUtilityService.generateJWT(userEntity));
                jwt.put("username",loginRequest.nombre());
                jwt.put("mensaje",String.format("Hola %s, has iniciado sesión con éxito!",loginRequest.nombre()));

            } else {
                jwt.put("error", "Autenticación Fallida");
            }

            return jwt;
        } catch (IllegalArgumentException e) {
            HashMap<String, String> error = new HashMap<>();
            System.err.println("Error generating JWT: " + e.getMessage());
            error.put("Error generating JWT", e.getMessage());
            return error;
        }
    }

    @Override
    public ResponseEntity<?> registrar(UsuarioDto userEntity){
        Usuario usuario = null;
        try {
            usuario = this.userRepository.findByNombre(userEntity.nombre()).orElse(null);

            if(!Objects.isNull(usuario)){
                return new ResponseEntity<>(new ErrorDto("Ya se encuentra registrado un usuario con ese nombre de usuario"), HttpStatus.BAD_REQUEST);
            }
            if(userEntity.idRol() == null){
                return new ResponseEntity<>(new ErrorDto("Debe seleccionar algún rol para el usuario"), HttpStatus.BAD_REQUEST);
            }
            if (!userEntity.correo().contains("@supervigilancia.gov.co")){
                return new ResponseEntity<>(new ErrorDto("EL correo electronico no corresponde al dominio de la supervigilancia"), HttpStatus.BAD_REQUEST);
            }
            Optional<Usuario> correoU = userRepository.findByCorreo(userEntity.correo());
            if (correoU.isPresent()){
                return new ResponseEntity<>(new ErrorDto("El correo ingresado ya se encuentra segistrado"),HttpStatus.BAD_REQUEST);
            }
            Set<Roles> roles = new HashSet<Roles>();
            Roles rol = rolRepository.findById(userEntity.idRol()).orElse(null);
            roles.add(rol);
            Usuario nuevoUsuario = new Usuario(userEntity);
            nuevoUsuario.setClave(passwordEncoder.encode(userEntity.contrasenha()));
            nuevoUsuario.setRoles(roles);
            userRepository.save(nuevoUsuario);

            MostrarUsuarioDto mostrarUsuarios = new MostrarUsuarioDto(nuevoUsuario);

            return new ResponseEntity<>(mostrarUsuarios, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Ha ocurrido un error en la base de datos"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean verificarContrasena(String enteredPassword, String storedPassword) {
        return passwordEncoder.matches(enteredPassword, storedPassword);
    }
    private static boolean esNombreValido(String nombre) {

        if (contieneNumero(nombre)) {
            return false;
        }
        if (tieneConsonantesSeguidas(nombre)) {
            return false;
        }
        return true;
    }
    private static boolean contieneNumero(String nombre) {
        for (char c : nombre.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    private static boolean tieneConsonantesSeguidas(String nombre) {
        String nombreMinusculas = nombre.toLowerCase();
        int contadorConsonantesSeguidas = 0;
        for (int i = 0; i < nombreMinusculas.length(); i++) {
            char letraActual = nombreMinusculas.charAt(i);
            if (esConsonante(letraActual)) {
                contadorConsonantesSeguidas++;
                if (contadorConsonantesSeguidas > 3) {
                    return true;
                }
            } else {
                contadorConsonantesSeguidas = 0;
            }
        }
        return false;
    }
    private static boolean esConsonante(char letra) {
        return "bcdfghjklmnpqrstvwxyz".indexOf(letra) != -1;
    }



}
