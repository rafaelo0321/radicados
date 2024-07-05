package com.superradicado.radicados.radicado.servicios.impl;

import com.superradicado.radicados.radicado.dto.crear.CrearDependenciaDto;
import com.superradicado.radicados.radicado.dto.mostrar.MostrarDependencia;
import com.superradicado.radicados.radicado.entidades.Dependencia;
import com.superradicado.radicados.radicado.repositorios.IDependenciaRepositorio;
import com.superradicado.radicados.radicado.servicios.IDependenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DependenciaImpl implements IDependenciaService {
    private final IDependenciaRepositorio iDependenciaRepositorio;

    public DependenciaImpl(IDependenciaRepositorio iDependenciaRepositorio) {
        this.iDependenciaRepositorio = iDependenciaRepositorio;
    }

    @Override
    public ResponseEntity<?> mostrarDependencias(){
        try {
            return new ResponseEntity<>(iDependenciaRepositorio.findAll().stream().map(MostrarDependencia::new).collect(Collectors.toList()), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> mostrarDependencia(Long id){
        try {
            return new ResponseEntity<>(iDependenciaRepositorio.findById(id).map(MostrarDependencia::new).orElse(null), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> agregarDependencia(CrearDependenciaDto crearDependenciaDto) {
        try {

            return new ResponseEntity<>(
                    new MostrarDependencia(
                            iDependenciaRepositorio.save(
                                    new Dependencia(crearDependenciaDto.numeroDependencia(),crearDependenciaDto.nombre()))),
                    HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
