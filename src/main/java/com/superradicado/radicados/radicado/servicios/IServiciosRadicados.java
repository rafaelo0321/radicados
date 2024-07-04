package com.superradicado.radicados.radicado.servicios;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoPresencialDto;
import com.superradicado.radicados.radicado.dto.mostrar.MostrarRadicadoDto;
import com.superradicado.radicados.radicado.entidades.Radicado;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IServiciosRadicados {
    Radicado generarRadicadoDesdeCorreoElectronico(Authentication authentication, CrearRadicadoDto crearRadicado);
    List<MostrarRadicadoDto> mostrarTodosRadicados();
    //Radicado generarRadicadosDeFormaPresencial(Authentication authentication, CrearRadicadoPresencialDto presencial);
}
