package com.vanessa.services;

import org.springframework.stereotype.Service;


import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.io.ByteArrayOutputStream;

import com.vanessa.entities.ServiceOrder;
import com.vanessa.repositories.OrderRepository;
import com.vanessa.resources.exceptions.ResourceNotFoundException;


@Service
public class OrderService {

    private OrderRepository orderRepository;

    public ServiceOrder createOrder(ServiceOrder serviceOrder) {
        byte[] pdfBytes = generatePdf(serviceOrder);
        //serviceOrder.setPdf(pdfBytes);

        return orderRepository.save(serviceOrder);
    }

    public ServiceOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public byte[] generatePdf(ServiceOrder serviceOrder) {
        // Criar um ByteArrayOutputStream para armazenar o PDF em memória
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Inicializar o PdfWriter e o PdfDocument
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Adicionar conteúdo ao PDF
        //document.add(new Paragraph("Order ID: " + serviceOrder.getId()));
        //document.add(new Paragraph("Customer: " + serviceOrder.getCustomerName()));
        //document.add(new Paragraph("Total: " + order.getTotalAmount()));
        // Adicione outros detalhes da ordem conforme necessário

        // Fechar o documento
        document.close();

        // Retornar o conteúdo do PDF como byte[]
        return outputStream.toByteArray();
    }

    public ServiceOrder saveOrder(ServiceOrder serviceOrder) {
        return orderRepository.save(serviceOrder);
    }
}