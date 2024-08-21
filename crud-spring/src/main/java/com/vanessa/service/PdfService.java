package com.vanessa.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {
    
    public byte[] generateOrderPdf(PdfService pdfService) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        document.add(new Paragraph("Ordem de Serviço"));
        //document.add(new Paragraph("Cliente: " + pdfService.getCustomer().getName()));
        //document.add(new Paragraph("Veículo: " + pdfService.getVehicle().getModel()));

        document.close();
        return baos.toByteArray();
    } 
}
