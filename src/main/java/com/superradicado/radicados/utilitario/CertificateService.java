package com.superradicado.radicados.utilitario;

import com.superradicado.radicados.radicado.entidades.Radicado;
import com.superradicado.radicados.radicado.repositorios.IRadicadoRepositorio;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class CertificateService implements GenerarCertificadoService {

    private final IRadicadoRepositorio repositorio;

    public CertificateService(IRadicadoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    private ByteArrayOutputStream generateQRCode(Radicado radicado){
        try {

            String qrContent = "Fecha: " + extraerFecha(radicado.getFechaCreacion()) + extraerHora(radicado.getFechaCreacion())+
                    "\n Dep: " + radicado.getDependencia().getNumeroDependencia() + "," +
                    "\n Folio: " + radicado.getFolio()+
                    "\n Usuario que atendío: " + radicado.getUsuario().getNombre();

            int width = 300;
            int height = 300;

            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, width, height);

            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", outputStream);

            return outputStream;
        }catch (IOException | WriterException e){
            System.err.println(e.getMessage());
            return new ByteArrayOutputStream();
        }
    }
    private String extraerFecha(LocalDateTime fecha){
        return fecha.format(DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'de' yyyy", new Locale("es", "CO")));
    }
    private String extraerHora(LocalDateTime fecha){
        return fecha.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public byte[] crearSelloDeImpresion(String radicado) {
        try {
            Radicado selloDeImpresion = repositorio.findByNumeroRadicado(radicado);

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            //fuentes cargas desde el resources
            InputStream fontStream = getClass().getResourceAsStream("/templates/font/verdana.ttf");
            PDType0Font customFont = PDType0Font.load(document, fontStream);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // Cargar la imagen
            //InputStream imageStream = getClass().getResourceAsStream();
            PDImageXObject logoFondoBn = PDImageXObject.createFromFile("/templates/img/logobn.png", document);
            // Dibujar la imagen en el documento PDF
            contentStream.drawImage(logoFondoBn, 40, 350, 500, 140);
            //Inicio de textos
            contentStream.beginText();

            contentStream.setFont(customFont, 11);
            contentStream.newLineAtOffset(50, 600);
            contentStream.showText("La Superintendencia De Vigilancia y Seguridad Privada");
            contentStream.setFont(customFont, 11);
            contentStream.newLineAtOffset(180, -35);
            contentStream.showText("Certifica:");
            contentStream.newLineAtOffset(-180, -50);

            //fin de textos
            contentStream.endText();

            //Dibujar la imagen en el documento PDF
            ByteArrayOutputStream qrOutputStream = generateQRCode(selloDeImpresion);
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, qrOutputStream.toByteArray(), "QRCode");

            contentStream.drawImage(pdImage, 500, 700, 70, 70);

            // Cargar la imagen y Dibuja la imagen en el documento PDF
            PDImageXObject image = PDImageXObject.createFromFile("src/main/resources/templates/img/logo.png", document);
            contentStream.drawImage(image, 50, 720, 170, 35);


            contentStream.close();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            document.save(output);

            document.close();

            return output.toByteArray();
        }catch (IOException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
}