package com.vanessa.controllers;

import com.vanessa.entities.UsedItems;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api/used-items")
public class UsedItemsController {

    @PostMapping("/calculateAmount")
    public BigDecimal calculateAmount(int totalQuantity, double unitPrice) {
        return BigDecimal.valueOf(totalQuantity).multiply(BigDecimal.valueOf(unitPrice));
    }
    
}
