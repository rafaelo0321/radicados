package com.superradicado.radicados.seguridad.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
public class CorsConfig {
        @Bean
        public CorsConfigurationSource corsConfigurationSource(){
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration cors = new CorsConfiguration();
            cors.addAllowedHeader("*");
            cors.addAllowedMethod("*");
            cors.setAllowCredentials(true);
            cors.addAllowedOrigin("http://localhost:5174");
            //cambiar aqui
            cors.addAllowedOrigin("http://localhost:5173");
            source.registerCorsConfiguration("/**", cors);
            return source;
        }
}