package com.superradicado.radicados.seguridad.configuracion;

import com.superradicado.radicados.seguridad.validaciones.ValidacionesDeUsuarios;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfig {

    @Bean
    public ValidacionesDeUsuarios validacionesDeUsuarios() {
        return new ValidacionesDeUsuarios();
    }
}
