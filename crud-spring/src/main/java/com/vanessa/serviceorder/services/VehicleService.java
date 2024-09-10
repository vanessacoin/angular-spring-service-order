package com.vanessa.serviceorder.services;

import com.vanessa.serviceorder.entities.Vehicle;
import com.vanessa.serviceorder.repositories.VehicleRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        Optional<Vehicle> obj = vehicleRepository.findById(id);
        return obj.get();
    }

}
