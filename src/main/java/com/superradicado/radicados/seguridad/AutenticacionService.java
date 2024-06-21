package com.superradicado.radicados.seguridad;


import com.superradicado.radicados.usuario.entidades.Roles;
import com.superradicado.radicados.usuario.entidades.Usuario;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class AutenticacionService implements UserDetailsService {


    private final IUsuarioRepositorio usuarioRepository;

    public AutenticacionService(IUsuarioRepositorio usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> u = usuarioRepository.findByNombre(username);
        if (u.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Usuario no encontrado por este nombre %s", username));
        }
        return new User(
                u.get().getNombre(),
                u.get().getClave(),
                true,
                true,
                true,
                true,
                mapeoDeRolesAGrantedAuth(u.get().getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapeoDeRolesAGrantedAuth(Set<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNombre())).toList();
    }
}