package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.radicado.dto.crear.CrearRadicadoDto;
import com.superradicado.radicados.utilitario.GenerarCertificadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sello")
public class GenerarCertificadoController {
    private final Logger log = LoggerFactory.getLogger(GenerarCertificadoController.class);

    private final GenerarCertificadoService pdfService;

    public GenerarCertificadoController(GenerarCertificadoService pdfServie) {
        this.pdfService = pdfServie;
    }


    @GetMapping(value = "/generar/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarSelloRadicado(Authentication authentication, @RequestBody CrearRadicadoDto numeroRadicado){
        try {
            byte[] pdf = pdfService.crearSelloDeImpresion(authentication,numeroRadicado);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doc_"+numeroRadicado+".pdf");
            log.info("Se generó correctamente el sello");
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
