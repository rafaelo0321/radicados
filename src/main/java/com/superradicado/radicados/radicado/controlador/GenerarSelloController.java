package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.auditoria.servicios.AuditoriaService;
import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.utilitario.GenerarSelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sello")
public class GenerarSelloController {
    private final Logger log = LoggerFactory.getLogger(GenerarSelloController.class);

    private final GenerarSelloService pdfService;
    private final AuditoriaService auditoria;

    public GenerarSelloController(GenerarSelloService pdfServie, AuditoriaService auditoria) {
        this.pdfService = pdfServie;
        this.auditoria = auditoria;
    }


    @GetMapping(value = "/generar/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarSelloRadicado(Authentication authentication, @RequestBody CrearRadicadoDto crearRadicado){
        try {

            byte[] pdf = pdfService.crearSelloDeImpresion(authentication,crearRadicado);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doc_"+crearRadicado.asunto()+".pdf");
            log.info("Se generó correctamente el sello");
            auditoria.crearAuditoria("El usuario generó decarga de un archivo de pdf con el asunto "+crearRadicado.asunto(), HttpMethod.GET.toString(),authentication);
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
