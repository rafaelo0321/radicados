package com.superradicado.radicados.radicado.servicios.impl;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.dto.mostrar.MostrarRadicadoDto;
import com.superradicado.radicados.radicado.entidades.Radicado;
import com.superradicado.radicados.radicado.repositorios.IDespendenciaRepositorio;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;
import com.superradicado.radicados.radicado.servicios.IServiciosRadicados;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImplementacionRadicados implements IServiciosRadicados {
    private final IRadicadoRepositorio iRadicadoRepositorio;
    private final IDespendenciaRepositorio iDespendenciaRepositorio;

    public ImplementacionRadicados(IRadicadoRepositorio iRadicadoRepositorio, IDespendenciaRepositorio iDespendenciaRepositorio) {
        this.iRadicadoRepositorio = iRadicadoRepositorio;
        this.iDespendenciaRepositorio = iDespendenciaRepositorio;
    }

    public void guardarRadicado(Radicado radicado){
        iRadicadoRepositorio.save(radicado);
    }

    public ResponseEntity<?> generarRadicado(CrearRadicadoDto crearRadicado){
        try{
            Radicado nuevoRadicado = new Radicado(crearRadicado);

            return new ResponseEntity<>(new MostrarRadicadoDto(nuevoRadicado),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generarConsecutivo(){
        String numero = String.valueOf(iRadicadoRepositorio.count());

        return numero;
    }

}
