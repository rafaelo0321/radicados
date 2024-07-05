package com.superradicado.radicados.radicado.servicios;

import com.superradicado.radicados.radicado.dto.crear.CrearDependenciaDto;
import org.springframework.http.ResponseEntity;

public interface IDependenciaService {
    ResponseEntity<?> mostrarDependencias();
    public ResponseEntity<?> mostrarDependencia(Long id);
    public ResponseEntity<?> agregarDependencia(CrearDependenciaDto crearDependenciaDto);
}
