package com.superradicado.radicados;

import com.superradicado.radicados.radicado.entidades.Dependencia;
import com.superradicado.radicados.radicado.repositorios.IDespendenciaRepositorio;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;
import com.superradicado.radicados.usuario.entidades.Roles;
import com.superradicado.radicados.usuario.entidades.Usuario;
import com.superradicado.radicados.usuario.repositorios.IRoleRepositorio;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class RadicadosApplication {
	private final PasswordEncoder passwordEncoder;

    public RadicadosApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(RadicadosApplication.class, args);
	}
	@Bean
	CommandLineRunner init(IDespendenciaRepositorio iDespendenciaRepositorio, IUsuarioRepositorio iUsuarioRepositorio, IRoleRepositorio roleRepositorio){
		return args -> {
			/*Dependencia dependencia = new Dependencia( 100,"DESPACHO SUPERINTENDENTE DE VIGILANCIA Y SEGURIDAD PRIVADA");
			Dependencia dependenciaDos = new Dependencia( 200,"OFICINA DE COMUNICACIONES");
			iDespendenciaRepositorio.save(dependencia);
			iDespendenciaRepositorio.save(dependenciaDos);

			Set<Roles> roles = new HashSet<>();
			roles.add(roleRepositorio.save(new Roles("User")));
			roles.add(roleRepositorio.save(new Roles("Admin")));

			Usuario usuario = new Usuario("prueba",passwordEncoder.encode("Contraseña.123"),"correo@correo.com");
			usuario.setRoles(roles);
			iUsuarioRepositorio.save(usuario);*/
		};
	}
}
