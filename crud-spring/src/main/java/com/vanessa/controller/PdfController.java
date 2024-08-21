package com.vanessa.controller;

/* import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import com.vanessa.crud_spring.service.PdfService; 

@RestController
@RequestMapping("/api/orders") */
public class PdfController {
    
    /* @Autowired
    private PdfService pdfService;

    @GetMapping("/{orderId}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long orderId) {
        // Recupere a ordem de serviço pelo ID
        ServiceOrder order = orderService.findById(orderId);
        byte[] pdfContent = pdfService.generateOrderPdf(order);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"order.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    } */
}
