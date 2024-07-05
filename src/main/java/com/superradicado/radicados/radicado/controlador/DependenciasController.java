package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.radicado.dto.crear.CrearDependenciaDto;
import com.superradicado.radicados.radicado.servicios.IDependenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dependencia")
public class DependenciasController {
    private final IDependenciaService iDependenciaService;

    public DependenciasController(IDependenciaService iDependenciaService) {
        this.iDependenciaService = iDependenciaService;
    }
    @GetMapping("/todos")
    public ResponseEntity<?> mostrarTodosLasDependencias(){
        return iDependenciaService.mostrarDependencias();
    }
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> mostrarDependencia(@PathVariable("id") Long id){
        return iDependenciaService.mostrarDependencia(id);
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agrgarDependencia(@RequestBody CrearDependenciaDto dto){
        return iDependenciaService.agregarDependencia(dto);
    }
}
