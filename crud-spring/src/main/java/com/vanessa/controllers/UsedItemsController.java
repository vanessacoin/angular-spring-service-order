package com.vanessa.controllers;

import com.vanessa.services.UsedItemsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api/used-items")
public class UsedItemsController {

    private final UsedItemsService usedItemsService;

    public UsedItemsController(UsedItemsService usedItemsService) {
        this.usedItemsService = usedItemsService;
    }

    @PostMapping("/calculateAmount")
    public BigDecimal calculateAmount(
            @RequestParam BigDecimal totalQuantity,
            @RequestParam double unitPrice) {
        return totalQuantity
                .multiply(BigDecimal.valueOf(unitPrice))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsedItem(@PathVariable Long id) {
        usedItemsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
