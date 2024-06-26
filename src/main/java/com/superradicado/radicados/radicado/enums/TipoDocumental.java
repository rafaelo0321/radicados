package com.superradicado.radicados.radicado.enums;

public enum TipoDocumental {
    MEMORANDOS(3),CIRCULARES(5),AUTOS(8),RESOLUCIONES(7),OFICIOS(1);

    TipoDocumental(int i) {
    }

    public String mostrarNumero(){
        return this.name();
    }

}
