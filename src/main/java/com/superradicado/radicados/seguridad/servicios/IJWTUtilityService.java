package com.superradicado.radicados.seguridad.servicios;

import com.nimbusds.jwt.JWTClaimsSet;
import com.superradicado.radicados.usuario.entidades.Usuario;

import java.util.Optional;

public interface IJWTUtilityService {
    String generateJWT(Optional<Usuario> user);

    JWTClaimsSet parseJWT(String jwt);

}
