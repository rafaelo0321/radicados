package com.superradicado.radicados.radicado.servicios;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoPresencialDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IServiciosRadicados {
    ResponseEntity<?> generarRadicadoDesdeCorreoElectronico(Authentication authentication, CrearRadicadoDto crearRadicado);
    ResponseEntity<?> mostrarTodosRadicados();
    ResponseEntity<?> generarRadicadosDeFormaPresencial(Authentication authentication, CrearRadicadoPresencialDto presencial);
}
