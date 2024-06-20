package com.superradicado.radicados.usuario.enums;

public enum NombreRoles {
    ADMIN, USER, CONSULTOR,AUDITOR;

    private static final String PREFIX = "ROLE_";

    public String getNombreCompleto() {
        return PREFIX + this.name();
    }

    public String getSimpleRoleName() {
        return this.name();
    }
}
