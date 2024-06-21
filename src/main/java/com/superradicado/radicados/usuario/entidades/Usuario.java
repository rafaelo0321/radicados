package com.superradicado.radicados.usuario.entidades;


import com.superradicado.radicados.usuario.dto.UsuarioDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Table(name = "TD_USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;
    private String nombre;
    private String clave;
    private String correo;
    private Boolean estado;
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCracion;
    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualization;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_USUARIO_ROLE",
            joinColumns = {@JoinColumn(name = "USUARIO_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ID_ROL")})
    private Set<Roles> roles;

    public Usuario() {
        this.estado = false;
        this.fechaCracion = LocalDateTime.now();
    }

    public Usuario(String nombre, String clave, String correo) {
        this.nombre = nombre;
        this.clave = clave;
        this.correo = correo;
        this.estado = false;
        this.fechaCracion = LocalDateTime.now();
    }

    public Usuario(String nombre, String clave, Set<Roles> roles) {
        this.nombre = nombre;
        this.clave = clave;
        this.roles = roles;
        this.estado = false;
        this.fechaCracion = LocalDateTime.now();
    }

    public Usuario(UsuarioDto userEntity) {
        this.nombre = userEntity.nombre();
        this.correo = userEntity.correo();
        this.estado = false;
        this.fechaCracion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void addRoles(Roles rol) {
        //rol.setUsuario(this);
        roles.add(rol);
    }

    public void actualizarUsuario(UsuarioDto dto){
        if (dto.nombre() != null){
            this.nombre = dto.nombre();
        }
        if (dto.correo() != null){
            this.correo = dto.correo();
        }
        if (dto.contrasenha() != null){
            this.clave = dto.contrasenha();
        }
        this.fechaActualization=LocalDateTime.now();
    }
    public void darDebaja(){
        this.estado = true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }


    public LocalDateTime getFechaCracion() {
        return fechaCracion;
    }

    public void setFechaCracion(LocalDateTime fechaCracion) {
        this.fechaCracion = fechaCracion;
    }

    public LocalDateTime getFechaActualization() {
        return fechaActualization;
    }

}
