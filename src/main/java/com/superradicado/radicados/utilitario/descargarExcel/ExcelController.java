package com.superradicado.radicados.utilitario.descargarExcel;

import com.superradicado.radicados.radicado.entidades.Radicado;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class ExcelController {


    private final RadicadoService radicadoService;
    private final IRadicadoRepositorio iRadicadoRepositorio;

    public ExcelController(RadicadoService radicadoService, IRadicadoRepositorio iRadicadoRepositorio) {
        this.radicadoService = radicadoService;
        this.iRadicadoRepositorio = iRadicadoRepositorio;
    }

    @GetMapping("/descargarRadicados")
    public ResponseEntity<byte[]> descargarRadicados() {

        // Obtener los radicados por persona y fecha
        List<Radicado> radicados = iRadicadoRepositorio.findAll();

        // Generar el archivo Excel
        radicadoService.generarExcel(radicados);

        // Cargar el archivo generado para descargarlo
        byte[] contents = new byte[0];
        try {
            contents = Files.readAllBytes(Paths.get("radicados.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=radicados.xlsx");

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }
}
