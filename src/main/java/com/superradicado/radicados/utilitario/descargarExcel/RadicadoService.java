package com.superradicado.radicados.utilitario.descargarExcel;
import com.superradicado.radicados.radicado.entidades.Radicado;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RadicadoService {
    private final IRadicadoRepositorio radicadoRepository;

    public RadicadoService(IRadicadoRepositorio radicadoRepository) {
        this.radicadoRepository = radicadoRepository;
    }

    public List<Radicado> getRadicadosDelDia() {
        LocalDateTime ahora = LocalDateTime.now();
        return radicadoRepository.findByFechaCreacionBetween(ahora.minusDays(10), ahora);
    }
    public ByteArrayOutputStream generarExcel(){
        try {
            List<Radicado> radicados = getRadicadosDelDia();

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Radicados");

            // Crear cabeceras y llenar hoja con datos de radicados
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NumeroRadicado");
            headerRow.createCell(1).setCellValue("FechaCreacion");
            headerRow.createCell(2).setCellValue("NombreEmpresa");
            headerRow.createCell(3).setCellValue("PersonaQueRadica");
            headerRow.createCell(4).setCellValue("Asunto");
            headerRow.createCell(5).setCellValue("Descripcion");
            headerRow.createCell(6).setCellValue("Folio");
            headerRow.createCell(7).setCellValue("Anexos");

            int rowNum = 1;
            for (Radicado radicado : radicados) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(radicado.getNumeroRadicado());
                row.createCell(1).setCellValue(radicado.getFechaCreacion().toString());
                row.createCell(2).setCellValue(radicado.getNombreEmpresa());/**/
                row.createCell(3).setCellValue(radicado.getPersonaQueRadica());
                row.createCell(4).setCellValue(radicado.getAsunto());
                row.createCell(5).setCellValue(radicado.getDescripcion());
                row.createCell(6).setCellValue(radicado.getFolio());
                row.createCell(7).setCellValue(radicado.getAnexos());

            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;
        }catch (IOException e){
            return null;
        }

    }
}
