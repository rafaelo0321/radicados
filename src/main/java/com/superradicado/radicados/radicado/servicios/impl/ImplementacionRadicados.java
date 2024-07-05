package com.superradicado.radicados.radicado.servicios.impl;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.dto.mostrar.MostrarRadicadoDto;
import com.superradicado.radicados.radicado.entidades.Radicado;
import com.superradicado.radicados.radicado.enums.TipoDocumental;
import com.superradicado.radicados.radicado.repositorios.IDependenciaRepositorio;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;
import com.superradicado.radicados.radicado.servicios.IServiciosRadicados;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImplementacionRadicados implements IServiciosRadicados {

    private final Logger log = LoggerFactory.getLogger(ImplementacionRadicados.class);

    private static int consecutivo = 0;
    private static int ultimoAnio = LocalDate.now().getYear();


    private final IRadicadoRepositorio iRadicadoRepositorio;
    private final IDependenciaRepositorio iDependenciaRepositorio;
    private final IUsuarioRepositorio iUsuarioRepositorio;

    public ImplementacionRadicados(IRadicadoRepositorio iRadicadoRepositorio, IDependenciaRepositorio iDependenciaRepositorio, IUsuarioRepositorio iUsuarioRepositorio) {
        this.iRadicadoRepositorio = iRadicadoRepositorio;
        this.iDependenciaRepositorio = iDependenciaRepositorio;
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }

    public void guardarRadicado(Radicado radicado){
        iRadicadoRepositorio.save(radicado);
    }
    @Override
    public Radicado generarRadicadoDesdeCorreoElectronico(Authentication authentication, CrearRadicadoDto crearRadicado){

        try{
            Radicado nuevoRadicado = new Radicado(crearRadicado);
            String numeroDependencia = String.valueOf(iDependenciaRepositorio.findByNombre(crearRadicado.nombreDependencia()).getNumeroDependencia());
            String numeroDeRadicado = Year.now().getValue() + numeroDependencia + generarConsecutivo() + numeroEnumTipoDocumental(crearRadicado.tipoDocumental())+ crearRadicado.contingencia();
            nuevoRadicado.setNumeroRadicado(numeroDeRadicado);
            nuevoRadicado.setDependencia(iDependenciaRepositorio.findByNombre(crearRadicado.nombreDependencia()));
            nuevoRadicado.setUsuario(iUsuarioRepositorio.findByNombre(authentication.getName()).orElse(null));

            if (crearRadicado.asunto().isEmpty() || crearRadicado.asunto() == null){
                nuevoRadicado.setAsunto("Radicado de forma presencial");
                nuevoRadicado.setDescripcion("Se radicó de forma presencial en las instalaciones de la supervigilancia por el usuario: "+ authentication.getName());
                nuevoRadicado.setPersonaQueRadica(authentication.getName());
            }

            guardarRadicado(nuevoRadicado);

            log.info("Se generó correctamente el radicado de entrada");
            return nuevoRadicado;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
    @Override
    public List<MostrarRadicadoDto> mostrarTodosRadicados(){
        try {
            log.info("Se muestra un listado con todos los radicados ordenados por la fecha");
            return iRadicadoRepositorio.findAll().stream().map(MostrarRadicadoDto::new).collect(Collectors.toList());
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("e.getMessage()");
            return null;
        }
    }

    @Override
    public List<Radicado> listadoPorDependencia(Integer numero) {
        return iRadicadoRepositorio.findByDependenciaNumeroDependencia(numero);
    }
   /* @Override
    public Radicado generarRadicadosDeFormaPresencial(Authentication authentication, CrearRadicadoPresencialDto presencial){
        try {
            Radicado radicado = new Radicado(presencial);
            String numeroDependencia = String.valueOf(iDespendenciaRepositorio.findByNombre(presencial.nombreDependencia()).getNumeroDependencia());
            String numeroDeRadicado = Year.now().getValue() + numeroDependencia + generarConsecutivo() + numeroEnumTipoDocumental(presencial.tipoDocumental())+ presencial.contingencia();
            radicado.setNumeroRadicado(numeroDeRadicado);
            radicado.setAsunto("Sin Asunto");
            radicado.setDescripcion("Se radicó de forma presencial en las instalaciones de la supervigilancia por: "+ authentication.getName());
            radicado.setPersonaQueRadica(authentication.getName());
            radicado.setDependencia(iDespendenciaRepositorio.findByNombre(presencial.nombreDependencia()));
            radicado.setUsuario(iUsuarioRepositorio.findByNombre(authentication.getName()).orElse(null));
            guardarRadicado(radicado);

            log.info("Se generó correctamente el radicado de forma presencial");
            return radicado;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }*/

    private static String generarConsecutivo(){
        int anioActual = LocalDate.now().getYear();
        if (anioActual > ultimoAnio) {
            consecutivo = 0;
            ultimoAnio = anioActual;
        }
        String formatoConsecutivo = String.format("%06d", consecutivo);
        consecutivo++;
        return formatoConsecutivo;
    }
    private static String numeroEnumTipoDocumental(TipoDocumental tipoDocumental){
        return switch (tipoDocumental) {
            case OFICIOS -> "1";
            case MEMORANDOS -> "3";
            case CIRCULARES -> "5";
            case RESOLUCIONES -> "7";
            case AUTOS -> "8";
        };
    }

}
