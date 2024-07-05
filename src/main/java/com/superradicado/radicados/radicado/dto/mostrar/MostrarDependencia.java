package com.superradicado.radicados.radicado.dto.mostrar;

import com.superradicado.radicados.radicado.entidades.Dependencia;

import java.util.List;
import java.util.stream.Collectors;

public record MostrarDependencia(Long id,Integer numeroDependencia,String nombre, List<MostrarRadicadoDto> radicados) {
    public MostrarDependencia(Dependencia dependencia) {
        this(dependencia.getId(), dependencia.getNumeroDependencia(), dependencia.getNombre(),
                dependencia.getRadicados().stream().map(MostrarRadicadoDto::new).collect(Collectors.toList()));
    }
}
