package com.superradicado.radicados.auditoria.entidades;

import com.superradicado.radicados.usuario.entidades.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "AUDITORIA")
public class Auditoria
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "FECHA_TRANSACCION")
    private LocalDate fecha;

    @Column(name = "TIPO_TRANSACCION")
    private String tipoTransaccion;
    @Column(name = "OBSERVACIONES")
    private String observacion;

    @Column(name = "NOMBRE_EQUIPO")
    private String nombreEquipo;

    @Column(name = "DIRECCION_IP")
    private String direccionIp;

    public Auditoria() {
    }

    public Auditoria(Usuario usuario, LocalDate fecha, String tipoTransaccion, String observacion, String nombreEquipo, String direccionIp) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.tipoTransaccion = tipoTransaccion;
        this.observacion = observacion;
        this.nombreEquipo = nombreEquipo;
        this.direccionIp = direccionIp;
    }
    /*public Auditoria(CrearAuditoriaDto dto) {
        this.tipoTransaccion = dto.tipoTransaccion();
        this.observacion = dto.observacion();
    }*/

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaTransaccion() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }
}
