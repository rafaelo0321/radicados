package com.superradicado.radicados.usuario.dto;

public class RespuestaValidacionesDTO {
    private int numOfErrors;
    private String message;

    public int getNumOfErrors() {
        return numOfErrors;
    }

    public void setNumOfErrors(int numOfErrors) {
        this.numOfErrors = numOfErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
