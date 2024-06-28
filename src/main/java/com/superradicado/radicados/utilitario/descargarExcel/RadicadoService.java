package com.superradicado.radicados.utilitario.descargarExcel;
import com.superradicado.radicados.radicado.entidades.Radicado;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RadicadoService {

    // Método para generar el archivo Excel
    public void generarExcel(List<Radicado> radicados) {
        try {
            // Crear un nuevo libro de trabajo Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Radicados");

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Número");
            headerRow.createCell(1).setCellValue("Fecha");

            // Llenar los datos de los radicados
            int rowNum = 1;
            for (Radicado radicado : radicados) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(radicado.getNumeroRadicado());
                row.createCell(1).setCellValue(radicado.getFechaCreacion().toString());
            }

            // Escribir el libro de trabajo a un archivo
            FileOutputStream fileOut = new FileOutputStream("radicados.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            // Liberar recursos
            //workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
