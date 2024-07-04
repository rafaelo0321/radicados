package com.superradicado.radicados.radicado.controlador;

import com.superradicado.radicados.auditoria.servicios.AuditoriaService;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;

import com.superradicado.radicados.utilitario.descargarDocumentos.RadicadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/radicados")
public class GenerarExcelController {
    private final Logger LOG = LoggerFactory.getLogger(GenerarExcelController.class);

    private final RadicadoService radicadoService;
    private final AuditoriaService auditoria;

    public GenerarExcelController(RadicadoService radicadoService, AuditoriaService auditoria) {
        this.radicadoService = radicadoService;
        this.auditoria = auditoria;
    }

    @GetMapping(value = "/descargar/delDia", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> descargarRadicadosExcel(Authentication authentication) {
        try {
            auditoria.crearAuditoria("El usuario generó decarga de un archivo de excel con los radicados que se generaron en el día", HttpMethod.GET.toString(),authentication);
            LocalDateTime ahora = LocalDateTime.now();
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=radicados_" + LocalDate.now().toString() + ".xlsx");
            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(radicadoService.generarExcel(ahora.minusHours(16)).toByteArray())));
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/descargar/historico", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> descargarRadicadosExcelTodos(Authentication authentication) {
        try {
            auditoria.crearAuditoria("El usuario generó decarga de un archivo de excel con los radicados que se generaron desde el inicio del aplicativo hasta el dia de hoy", HttpMethod.GET.toString(),authentication);

            LocalDateTime ahora = LocalDateTime.now();
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=radicados_" + LocalDate.now().toString() + ".xlsx");
            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(radicadoService.generarExcel(ahora.minusYears(5)).toByteArray())));
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     *  @RequestParam("fechaInicio") formato --> 2024-07-02T15:13:16Z
     * */
    @GetMapping(value = "/descargar/rango/fecha", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> descargarRadicadosExcelRangoDeFechas(
            @RequestParam("fechaInicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fechaInicio,
            Authentication authentication
    ) {
        try {
            auditoria.crearAuditoria("El usuario generó decarga de un archivo de excel con los radicados que se generaron entre la fecha "+fechaInicio.toString()+" hasta el dia de hoy", HttpMethod.GET.toString(),authentication);

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=radicados_" + LocalDate.now().toString() + ".xlsx");
            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(radicadoService.generarExcel(fechaInicio).toByteArray())));
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/descargar/dependencia/{numeroDependencia}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> descargarRadicadosExcelPorDependencia(
            @PathVariable("numeroDependencia")
            Integer numeroDependencia,
            Authentication authentication
    ) {
        try {
            auditoria.crearAuditoria("El usuario generó decarga de un archivo de excel con los radicados que se generaron segun la dependencia "+numeroDependencia+" hasta el dia de hoy", HttpMethod.GET.toString(),authentication);

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=radicados_dependencia" +numeroDependencia + ".xlsx");
            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new ByteArrayInputStream(radicadoService.generarExcelPorDependencia(numeroDependencia).toByteArray())));
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
