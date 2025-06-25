package com.vanessa.services;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.vanessa.entities.ServiceOrder;
import com.vanessa.repositories.OrderRepository;
import com.vanessa.resources.exceptions.ResourceNotFoundException;

import java.io.ByteArrayOutputStream;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ServiceOrder createOrder(ServiceOrder order) {
        System.out.println("Recebendo ordem: " + order);
        if (order.getCustomerId() == null || order.getVehicleId() == null) {
            throw new RuntimeException("Cliente e veículo são obrigatórios");
        }
        return orderRepository.save(order);
    }

    public ServiceOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public byte[] generatePdf(ServiceOrder serviceOrder) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Order ID: " + serviceOrder.getId()));
        document.add(new Paragraph("Customer: " + serviceOrder.getCustomerName()));

        document.close();

        return outputStream.toByteArray();
    }

    public ServiceOrder saveOrder(ServiceOrder serviceOrder) {
        return orderRepository.save(serviceOrder);
    }
}