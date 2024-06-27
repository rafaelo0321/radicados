package com.superradicado.radicados.utilitario;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.radicado.entidades.Radicado;
import org.springframework.security.core.Authentication;

public interface GenerarCertificadoService {
    byte[] crearSelloDeImpresion(Authentication authentication, CrearRadicadoDto numero);
}
