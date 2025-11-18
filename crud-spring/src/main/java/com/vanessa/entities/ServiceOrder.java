package com.vanessa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceOrder implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant orderDate;
    private Long customerId;
    private String customerName;
    private String customerCpf;
    private String customerPhone;
    private String customerEmail;
    private Long vehicleId;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehiclePlate;
    private Integer vehicleKm;
    private Integer vehicleYear;
    private String vehicleColor;
    private Long vehicleCustomerId;
    private BigDecimal laborCost;
    private BigDecimal totalOrder;

    @Lob
    private byte[] pdf;

    @OneToMany(mappedBy = "serviceOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequestedService> requestedServices;

    @OneToMany(mappedBy = "serviceOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UsedItems> usedItems;
}