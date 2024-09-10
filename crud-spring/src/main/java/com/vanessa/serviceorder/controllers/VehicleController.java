package com.vanessa.serviceorder.controllers;

import com.vanessa.serviceorder.entities.Vehicle;
import com.vanessa.serviceorder.services.VehicleService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("api/vehicles")
public class VehicleController {

    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
}
