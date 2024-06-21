package com.superradicado.radicados.radicado.entidades;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.usuario.entidades.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RADICADOS")
public class Radicado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RADICADO")
    private Long id;
    @Column(name = "NUMERO_RADICADO", unique = true,nullable = false,length = 12)
    private String numeroRadicado;
    @Column(name = "NOMBRE_EMPRESA", unique = true,nullable = false,length = 200)
    private String nombreEmpresa;
    @Column(name = "NOMBRE_PERSONA_NATURAL", unique = true,nullable = false,length = 100)
    private String personaQueRadica;
    @Column(name = "ASUNTO", length = 100)
    private String asunto;
    @Column(name = "DESCRIPCION", length = 220)
    private String descripcion;
    private Integer folio;
    private Integer anexos;
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    //
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.EAGER)
    private Dependencia dependencia;

    public Radicado() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Radicado(CrearRadicadoDto crearRadicado) {
        this.fechaCreacion = LocalDateTime.now();
        this.anexos = crearRadicado.anexos();
        this.folio = crearRadicado.folio();
        this.descripcion = crearRadicado.descripcion();
        this.asunto = crearRadicado.asunto();
        this.personaQueRadica = crearRadicado.personaQueRadica();
        this.nombreEmpresa = crearRadicado.nombreEmpresa();
    }

    public Long getId() {
        return id;
    }

    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getPersonaQueRadica() {
        return personaQueRadica;
    }

    public void setPersonaQueRadica(String personaQueRadica) {
        this.personaQueRadica = personaQueRadica;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public Integer getAnexos() {
        return anexos;
    }

    public void setAnexos(Integer anexos) {
        this.anexos = anexos;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }
}
