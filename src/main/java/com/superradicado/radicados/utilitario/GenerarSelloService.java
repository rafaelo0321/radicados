package com.superradicado.radicados.utilitario;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import org.springframework.security.core.Authentication;

public interface GenerarSelloService {
    byte[] crearSelloDeImpresion(Authentication authentication, CrearRadicadoDto numero);
}
