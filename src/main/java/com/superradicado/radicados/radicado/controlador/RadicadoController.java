package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoPresencialDto;
import com.superradicado.radicados.radicado.servicios.IServiciosRadicados;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/radicados")
public class RadicadoController {
    private final IServiciosRadicados implementacionRadicados;

    public RadicadoController(IServiciosRadicados implementacionRadicados) {
        this.implementacionRadicados = implementacionRadicados;
    }
    @PostMapping("/generar/desde/correo")
    public ResponseEntity<?> generarRadicadoCorreo(@RequestBody CrearRadicadoDto crearRadicadoDto, Authentication auth){
        return implementacionRadicados.generarRadicadoDesdeCorreoElectronico(auth,crearRadicadoDto);
    }
    @PostMapping("/generar/presencial")
    public ResponseEntity<?> generarRadicadoPresencial(@RequestBody CrearRadicadoPresencialDto crearRadicadoDto, Authentication auth){
        return implementacionRadicados.generarRadicadosDeFormaPresencial(auth,crearRadicadoDto);
    }
    @GetMapping("/mostrar/todos")
    public ResponseEntity<?> mostrarTodos(){
        return implementacionRadicados.mostrarTodosRadicados();
    }
}
