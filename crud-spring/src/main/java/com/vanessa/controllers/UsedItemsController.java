package com.vanessa.controllers;

import com.vanessa.services.UsedItemsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api/used-items")
public class UsedItemsController {

    private final UsedItemsService usedItemsService;

    public UsedItemsController(UsedItemsService usedItemsService) {
        this.usedItemsService = usedItemsService;
    }

    @PostMapping("/calculateAmount")
    public BigDecimal calculateAmount(int totalQuantity, double unitPrice) {
        return BigDecimal.valueOf(totalQuantity).multiply(BigDecimal.valueOf(unitPrice));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsedItem(@PathVariable Long id) {
        usedItemsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
