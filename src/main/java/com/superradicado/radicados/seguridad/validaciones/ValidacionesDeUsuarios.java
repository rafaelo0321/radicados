package com.superradicado.radicados.seguridad.validaciones;


import com.superradicado.radicados.usuario.dto.RespuestaValidacionesDTO;
import com.superradicado.radicados.usuario.dto.UsuarioDto;

public class ValidacionesDeUsuarios {

    public RespuestaValidacionesDTO validate(UsuarioDto userEntity){
        RespuestaValidacionesDTO response = new RespuestaValidacionesDTO();

        response.setNumOfErrors(0);

        if (userEntity.nombre() == null || userEntity.nombre().length() < 3 || userEntity.nombre().length() > 15){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo PRIMER NOMBRE no puede ser nulo, tampoco puede tener menos de 3 caracteres ni mas de 15");
        }

        if (
                userEntity.correo() == null ||
                        !userEntity.correo().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo CORREO ELECTRONICO no es correcto");
        }

        if(
                userEntity.contrasenha() == null ||
                        !userEntity.contrasenha().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("La contraseña debe tener entre 8 y 16 caracteres, al menos un número, una minúscula y una mayúscula.");
        }
        if (
                userEntity.idRol() == null
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo el ROL no es correcto");
        }

        return response;
    }
}

