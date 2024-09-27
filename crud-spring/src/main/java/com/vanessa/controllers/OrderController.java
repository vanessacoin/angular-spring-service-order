package com.vanessa.controllers;

import com.vanessa.entities.ServiceOrder;
import com.vanessa.resources.exceptions.ResourceNotFoundException;
import com.vanessa.services.OrderService;
import com.vanessa.services.PdfService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    private PdfService pdfService; 

    @PostMapping
    public ResponseEntity<ServiceOrder> createOrder(@RequestBody ServiceOrder order) {
        ServiceOrder createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generateOrderPdf(@PathVariable("id") Long id) {
        ServiceOrder serviceOrder = orderService.getOrderById(id);
    if (serviceOrder == null) {
        throw new ResourceNotFoundException("Order not found with id " + id);
    }

    try {
        byte[] pdfBytes = pdfService.generateOrderPdf(serviceOrder);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}