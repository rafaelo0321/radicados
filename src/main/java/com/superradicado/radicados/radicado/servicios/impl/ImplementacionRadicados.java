package com.superradicado.radicados.radicado.servicios.impl;

import com.superradicado.radicados.radicado.controlador.RadicadoController;
import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.dto.mostrar.MostrarRadicadoDto;
import com.superradicado.radicados.radicado.entidades.Radicado;
import com.superradicado.radicados.radicado.repositorios.IDespendenciaRepositorio;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;
import com.superradicado.radicados.radicado.servicios.IServiciosRadicados;
import com.superradicado.radicados.usuario.entidades.Usuario;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;

@Service
public class ImplementacionRadicados implements IServiciosRadicados {

    private final Logger log = LoggerFactory.getLogger(ImplementacionRadicados.class);

    private static int consecutivo = 0;
    private static int ultimoAnio = LocalDate.now().getYear();


    private final IRadicadoRepositorio iRadicadoRepositorio;
    private final IDespendenciaRepositorio iDespendenciaRepositorio;
    private final IUsuarioRepositorio iUsuarioRepositorio;

    public ImplementacionRadicados(IRadicadoRepositorio iRadicadoRepositorio, IDespendenciaRepositorio iDespendenciaRepositorio, IUsuarioRepositorio iUsuarioRepositorio) {
        this.iRadicadoRepositorio = iRadicadoRepositorio;
        this.iDespendenciaRepositorio = iDespendenciaRepositorio;
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }

    public Radicado guardarRadicado(Radicado radicado){
        return iRadicadoRepositorio.save(radicado);
    }
    @Override
    public ResponseEntity<?> generarRadicado(CrearRadicadoDto crearRadicado){

        try{
            Radicado nuevoRadicado = new Radicado(crearRadicado);

            String numeroDependencia = String.valueOf(iDespendenciaRepositorio.findByNombre(crearRadicado.nombreDependencia()).getNumeroDependencia());
            String numeroDeRadicado = Year.now().getValue() + numeroDependencia + generarConsecutivo() + crearRadicado.tipoDocumental() + crearRadicado.contingencia();

            nuevoRadicado.setNumeroRadicado(numeroDeRadicado);
            nuevoRadicado.setDependencia(iDespendenciaRepositorio.findByNombre(crearRadicado.nombreDependencia()));
            nuevoRadicado.setUsuario(iUsuarioRepositorio.findById(1L).orElse(null));
            System.out.println("Hola "+nuevoRadicado);

            iRadicadoRepositorio.save(nuevoRadicado);

            log.info("Se generó correctamente el radicado de entrada");
            return new ResponseEntity<>(new MostrarRadicadoDto(nuevoRadicado),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generarConsecutivo(){
        int anioActual = LocalDate.now().getYear();
        if (anioActual > ultimoAnio) {
            consecutivo = 0;
            ultimoAnio = anioActual;
        }
        String formatoConsecutivo = String.format("%06d", consecutivo);
        consecutivo++;
        return formatoConsecutivo;
    }
}
