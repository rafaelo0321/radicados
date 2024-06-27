package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.utilitario.GenerarCertificadoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sello")
public class GenerarCertificadoController {


    private final GenerarCertificadoService pdfService;

    public GenerarCertificadoController(GenerarCertificadoService pdfServie) {
        this.pdfService = pdfServie;
    }


    @GetMapping(value = "/generar/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarSelloRadicado(@RequestParam String numeroRadicado){
        try {
            byte[] pdf = pdfService.crearSelloDeImpresion(numeroRadicado);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doc_"+numeroRadicado+".pdf");
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
