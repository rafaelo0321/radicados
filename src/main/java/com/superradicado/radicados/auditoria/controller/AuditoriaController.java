package com.superradicado.radicados.auditoria.controller;

import com.superradicado.radicados.auditoria.dto.mostrar.MostrarAuditoriaDto;
import com.superradicado.radicados.auditoria.entidades.Auditoria;
import com.superradicado.radicados.auditoria.servicios.AuditoriaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auditoria")
public class AuditoriaController {
    private final AuditoriaService service;

    public AuditoriaController(AuditoriaService service) {
        this.service = service;
    }
    @GetMapping("/mostrar/auditoria/{id}")
    public ResponseEntity<MostrarAuditoriaDto> mostrarAuditoria(@PathVariable Long id,Authentication authentication) {
        try {
            return new ResponseEntity<>(service.mostrarAuditoriaId(id,authentication), HttpStatus.OK);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new MostrarAuditoriaDto(new Auditoria()),HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/mostrar/auditoria-id-usuario/{id}")
    public ResponseEntity<List<MostrarAuditoriaDto>> mostrarDiagramaPorIdUsuario(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.mostrarAuditoriaPorIdUsuario(id),HttpStatus.OK);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/mostrar/todas")
    public ResponseEntity<List<MostrarAuditoriaDto>> getMostrarAuditoria() {
        try{
            return new ResponseEntity<>(service.getMostrarAuditoria(),HttpStatus.OK);
        }catch (Exception e) {
        System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/mostrar/auditoria-fechas")
    public ResponseEntity<List<MostrarAuditoriaDto>> obtenerAuditoriasPorFecha(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
            ) {
        try{
            return new ResponseEntity<>(service.obtenerAuditoriasPorFecha(fechaInicio,fechaFin),HttpStatus.OK);
        }catch (Exception e) {

        System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/mostrar/auditoria/usuario/fechas")
    public ResponseEntity<List<MostrarAuditoriaDto>> obtenerAuditoriasPorFechaYUsuario(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin){
        try{
            return new ResponseEntity<>(service.obtenerAuditoriasPorFechaYUsuario(usuarioId,fechaInicio,fechaFin),HttpStatus.OK);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
