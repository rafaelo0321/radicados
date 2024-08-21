package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.auditoria.servicios.AuditoriaService;
import com.superradicado.radicados.radicado.dto.mostrar.MostrarRadicadoDto;
import com.superradicado.radicados.radicado.servicios.IServiciosRadicados;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/radicados")
public class RadicadoController {
    private final IServiciosRadicados implementacionRadicados;
    private final AuditoriaService auditoria;

    public RadicadoController(IServiciosRadicados implementacionRadicados, AuditoriaService auditoria ) {
        this.implementacionRadicados = implementacionRadicados;
        this.auditoria = auditoria;
    }
    @GetMapping("/mostrar/todos")
    public ResponseEntity<?> mostrarTodos(Authentication authentication){
        auditoria.crearAuditoria("se muestra un listado con todos los radicados a la fecha ", HttpMethod.GET.toString(),authentication);
        return new ResponseEntity<>(implementacionRadicados.mostrarTodosRadicados(),HttpStatus.OK);
    }

    @GetMapping("mostrar/por_dependencia/{numero}")
    public ResponseEntity<List<?>> mostrarListadoDeRadicadosPorDependencia(@PathVariable("numero") int numero){
        return new ResponseEntity<>(implementacionRadicados.listadoPorDependencia(numero), HttpStatus.OK);
    }
}
