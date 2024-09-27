package com.vanessa.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity

public class UsedItems implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int totalQuantity;
    private float unitPrice;
    private float amount;
    @Column(name = "order_id")
    private ServiceOrder order;

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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ServiceOrder getOrder() {
        return order;
    }

    public void setOrder(ServiceOrder order) {
        this.order = order;
    }
}