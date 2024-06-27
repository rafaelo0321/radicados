package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.radicado.servicios.GenerarCertificadoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GenerarCertificadoController {


    private final GenerarCertificadoService pdfService;

    public GenerarCertificadoController(GenerarCertificadoService pdfServie) {
        this.pdfService = pdfServie;
    }


    @GetMapping(value = "/generar/certificado", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarCertificadoApo(@RequestParam String cedula){
        try {
            byte[] pdf = pdfService.crearCertificado();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doc_"+cedula+".pdf");
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
