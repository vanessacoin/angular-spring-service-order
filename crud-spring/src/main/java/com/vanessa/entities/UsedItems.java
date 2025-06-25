package com.vanessa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;

@Entity

public class UsedItems implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int totalQuantity;
    private float unitPrice;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;

    
    @ManyToOne
    @JoinColumn(name = "order_id")
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
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

    public BigDecimal totalAmount(int totalQuantity, float unitPrice) {
        BigDecimal quantity = BigDecimal.valueOf(totalQuantity);
        BigDecimal price = BigDecimal.valueOf(unitPrice);

        this.amount = quantity.multiply(price).setScale(2, RoundingMode.HALF_UP);
        return amount;
    }
}