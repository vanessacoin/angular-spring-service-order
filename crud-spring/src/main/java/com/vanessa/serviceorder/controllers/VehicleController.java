package com.vanessa.serviceorder.controllers;

import com.vanessa.serviceorder.entities.Vehicle;
import com.vanessa.serviceorder.repositories.VehicleRepository;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@AllArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    @GetMapping
    public List<Vehicle> list() {
        return vehicleRepository.findAll();
    }
}
