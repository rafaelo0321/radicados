package com.superradicado.radicados.usuario.dto;
import com.superradicado.radicados.usuario.entidades.Roles;
import com.superradicado.radicados.usuario.entidades.Usuario;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MapperUsuario {

    public Usuario dto2Entity(UsuarioDto dto,
                              Set<Roles> roles) {
        Usuario user = new Usuario();
        user.setNombre(dto.nombre());
        user.setCorreo(dto.correo());
        user.setClave(dto.contrasenha());
        user.setRoles(roles);

        return user;
    }

}