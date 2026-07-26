package com.vanessa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity

public class UsedItems implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal totalQuantity;
    private float unitPrice;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;

    
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private ServiceOrder serviceOrder;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ServiceOrder getserviceOrder() {
        return serviceOrder;
    }

    public void setserviceOrder(ServiceOrder order) {
        this.serviceOrder = order;
    }

    public BigDecimal totalAmount(BigDecimal totalQuantity, float unitPrice) {
    if (totalQuantity == null) {
        totalQuantity = BigDecimal.ZERO;
    }

    BigDecimal price = BigDecimal.valueOf(unitPrice);

    this.amount = totalQuantity.multiply(price).setScale(2, RoundingMode.HALF_UP);
    return amount;
}

    public void setOrder(ServiceOrder order) {
        this.serviceOrder = order;
    }
}