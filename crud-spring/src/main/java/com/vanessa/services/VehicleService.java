package com.vanessa.services;

import org.springframework.stereotype.Service;

import java.util.List;

import com.vanessa.entities.Vehicle;
import com.vanessa.repositories.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
