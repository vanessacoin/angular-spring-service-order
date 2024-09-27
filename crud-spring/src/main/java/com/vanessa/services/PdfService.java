package com.vanessa.services;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.vanessa.entities.ServiceOrder;

import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    
     public byte[] generateOrderPdf(ServiceOrder serviceOrder) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // Inicializa o PdfWriter e PdfDocument
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Adiciona conteúdo ao PDF

            document.add(new Paragraph("Order Details"));
            //document.add(new Paragraph("Order ID: " + serviceOrder.getId()));
            //document.add(new Paragraph("Order Date: " + order.getOrderDate().toString()));
            
            // Adicione outros detalhes da ordem conforme necessário
            // Exemplo de como adicionar mais conteúdo

            //document.add(new Paragraph("Customer: " + order.getCustomerName()));
            //document.add(new Paragraph("Vehicle: " + order.getVehicleModel()));
            
            // Se houver uma lista de serviços ou itens, adicione-os aqui

            // Fecha o documento
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to generate PDF", e);
        }

        return baos.toByteArray();
    }
}
