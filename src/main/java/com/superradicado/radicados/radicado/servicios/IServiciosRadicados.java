package com.superradicado.radicados.radicado.servicios;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import org.springframework.http.ResponseEntity;

public interface IServiciosRadicados {
    ResponseEntity<?> generarRadicado(CrearRadicadoDto crearRadicado);
}
