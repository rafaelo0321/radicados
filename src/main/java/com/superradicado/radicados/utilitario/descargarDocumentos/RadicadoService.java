package com.superradicado.radicados.utilitario.descargarDocumentos;
import com.superradicado.radicados.radicado.dto.mostrar.MostrarRadicadoDto;
import com.superradicado.radicados.radicado.entidades.Radicado;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RadicadoService {
    private final Logger LOG = LoggerFactory.getLogger(RadicadoService.class);
    private final IRadicadoRepositorio radicadoRepository;

    public RadicadoService(IRadicadoRepositorio radicadoRepository) {
        this.radicadoRepository = radicadoRepository;
    }

    private List<MostrarRadicadoDto> getRadicadosDelDia(LocalDateTime ahora) {
        LocalDateTime hoy = LocalDateTime.now();
        return radicadoRepository.findByFechaCreacionBetween(ahora, hoy).stream().map(MostrarRadicadoDto::new).collect(Collectors.toList());
    }
    private List<MostrarRadicadoDto> getRadicadosPorDependencia(Integer numeroDependencia) {
        return radicadoRepository.findByDependenciaNumeroDependencia(numeroDependencia).stream().map(MostrarRadicadoDto::new).collect(Collectors.toList());
    }

    public ByteArrayOutputStream generarExcel(LocalDateTime fecha){
        List<MostrarRadicadoDto> radicados = getRadicadosDelDia(fecha);
        LOG.info("Se generó un documento en excel, con los radicados, segun la fecha {}", fecha.toString());
        return generarEsquemaDelExcel(radicados);
    }

    public ByteArrayOutputStream generarExcelPorDependencia(Integer numeroDependencia){
        List<MostrarRadicadoDto> radicados = getRadicadosPorDependencia(numeroDependencia);
        LOG.info("Se generó un documento en excel, segun el numero de la dependencia {}", numeroDependencia);
        return generarEsquemaDelExcel(radicados);
    }
    private ByteArrayOutputStream generarEsquemaDelExcel(List<MostrarRadicadoDto> radicados){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Radicados");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NumeroRadicado");
            headerRow.createCell(1).setCellValue("FechaCreacion");
            headerRow.createCell(2).setCellValue("NombreEmpresa");
            headerRow.createCell(3).setCellValue("PersonaQueRadica");
            headerRow.createCell(4).setCellValue("Asunto");
            headerRow.createCell(5).setCellValue("Descripcion");
            headerRow.createCell(6).setCellValue("Folio");
            headerRow.createCell(7).setCellValue("Anexos");
            headerRow.createCell(8).setCellValue("Dependencia");
            headerRow.createCell(9).setCellValue("Usuario que Ingresó");
            int rowNum = 1;
            for (MostrarRadicadoDto radicado : radicados) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(radicado.numeroRadicado());
                row.createCell(1).setCellValue(radicado.fechaCreacion().toString());
                row.createCell(2).setCellValue(radicado.nombreEmpresa());/**/
                row.createCell(3).setCellValue(radicado.personaQueRadica());
                row.createCell(4).setCellValue(radicado.asunto());
                row.createCell(5).setCellValue(radicado.descripcion());
                row.createCell(6).setCellValue(radicado.folio());
                row.createCell(7).setCellValue(radicado.anexos());
                row.createCell(8).setCellValue(radicado.dependencia());
                row.createCell(9).setCellValue(radicado.usuario());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;
        }
        catch (IOException e){
            LOG.error("Se generó un error al crear el documento, {}",e.getMessage());
            return null;
        }
    }
}
