package com.superradicado.radicados.auditoria.servicios.impl;

import com.superradicado.radicados.auditoria.dto.mostrar.MostrarAuditoriaDto;
import com.superradicado.radicados.auditoria.entidades.Auditoria;
import com.superradicado.radicados.auditoria.repositorios.AuditoriaDao;
import com.superradicado.radicados.auditoria.servicios.AuditoriaService;
import com.superradicado.radicados.usuario.repositorios.IUsuarioRepositorio;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditoriaImplementado implements AuditoriaService {
    private final AuditoriaDao dao;
    private final IUsuarioRepositorio usr;

    public AuditoriaImplementado(AuditoriaDao dao, IUsuarioRepositorio usr) {
        this.dao = dao;
        this.usr = usr;
    }

    @Override
    public void crearAuditoria(String observacion, String tipoTransaccion,Authentication user){
        try {
            Auditoria nuevaAuditoria = new Auditoria();

            nuevaAuditoria.setUsuario(usr.findByNombre(user.getName()).orElse(null));
            nuevaAuditoria.setFecha(LocalDate.now());
            nuevaAuditoria.setObservacion(observacion);
            nuevaAuditoria.setTipoTransaccion(tipoTransaccion);
            nuevaAuditoria.setDireccionIp(getDirecionIp());
            nuevaAuditoria.setNombreEquipo(getNombreEquipo());

            dao.save(nuevaAuditoria);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public List<MostrarAuditoriaDto> getMostrarAuditoria(){
        return dao.findAll()
                .stream()
                .map(MostrarAuditoriaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public MostrarAuditoriaDto mostrarAuditoriaId(Long id, Authentication authentication) {

        crearAuditoria("Prueba de auditoria", HttpMethod.GET.toString(),authentication);
        return dao.findById(id).map(MostrarAuditoriaDto::new).orElse(null);
    }

    @Override
    public List<MostrarAuditoriaDto> mostrarAuditoriaPorIdUsuario(Long id) {
        return dao.findByUsuarioId(id)
                .stream()
                .map(MostrarAuditoriaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MostrarAuditoriaDto> obtenerAuditoriasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return dao.findByFechaBetween(fechaInicio, fechaFin)
                .stream()
                .map(MostrarAuditoriaDto::new)
                .collect(
                        Collectors
                                .toList());
    }
    @Override
    public List<MostrarAuditoriaDto> obtenerAuditoriasPorFechaYUsuario(Long usuarioId,LocalDate fechaInicio, LocalDate fechaFin) {
        return dao.findByUsuarioIdAndFechaBetween(usuarioId, fechaInicio, fechaFin)
                .stream()
                .map(MostrarAuditoriaDto::new)
                .collect(
                        Collectors
                                .toList());
    }
    private String getDirecionIp() {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            return ipAddress.getHostAddress();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    private String getNombreEquipo() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
