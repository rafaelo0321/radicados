package com.superradicado.radicados.utilitario.descargarExcel;

import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/radicados")
public class ExcelController {
    private final Logger LOG = LoggerFactory.getLogger(ExcelController.class);

    private final RadicadoService radicadoService;
    private final IRadicadoRepositorio iRadicadoRepositorio;

    public ExcelController(RadicadoService radicadoService, IRadicadoRepositorio iRadicadoRepositorio) {
        this.radicadoService = radicadoService;
        this.iRadicadoRepositorio = iRadicadoRepositorio;
    }

    @GetMapping(value = "/descargar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> descargarRadicadosExcel(

    ) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=radicados_" + LocalDate.now().toString() + ".xlsx");
            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(radicadoService.generarExcel().toByteArray())));
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
