package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.servicios.IServiciosRadicados;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/radicados")
public class RadicadoController {
    private final IServiciosRadicados implementacionRadicados;

    public RadicadoController(IServiciosRadicados implementacionRadicados) {
        this.implementacionRadicados = implementacionRadicados;
    }
    @PostMapping("/generar")
    public ResponseEntity<?> generarRadicado(@RequestBody CrearRadicadoDto crearRadicadoDto){
        return implementacionRadicados.generarRadicado(crearRadicadoDto);
    }
}
