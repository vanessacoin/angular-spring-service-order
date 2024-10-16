package com.vanessa.entities;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String brand;

    @NotBlank
    @NotNull
    @Length(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String model;

    @NotBlank
    @NotNull
    @Length(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String plate;

    @NotNull
    @Column(length = 4, columnDefinition = "YEAR", nullable = false)
    private int year;

    private String color;

    // Mapeamento da FK com Customer
    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "id")
    private Customer customer;

    public Vehicle() {
    }

    public Vehicle(final Long id, final String brand, final String model, final String plate, final int year, final String color, final Customer customer) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.year = year;
        this.color = color;
        this.customer = customer ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
