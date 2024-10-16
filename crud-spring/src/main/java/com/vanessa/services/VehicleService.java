package com.vanessa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.vanessa.entities.Customer;
import com.vanessa.entities.Vehicle;
import com.vanessa.repositories.CustomerRepository;
import com.vanessa.repositories.VehicleRepository;
import com.vanessa.resources.exceptions.ResourceNotFoundException;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private CustomerRepository customerRepository;

    public VehicleService(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));
    }

    public Vehicle saveVehicle(Vehicle vehicle) { 
        Customer customer = customerRepository.findById(vehicle.getCustomer().getId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        vehicle.setCustomer(customer);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Optional<Vehicle> existingVehicleOpt = vehicleRepository.findById(id);
        
        if (existingVehicleOpt.isPresent()) {
            Vehicle existingVehicle = existingVehicleOpt.get();
            existingVehicle.setBrand(updatedVehicle.getBrand());
            existingVehicle.setModel(updatedVehicle.getModel());
            existingVehicle.setPlate(updatedVehicle.getPlate());
            existingVehicle.setYear(updatedVehicle.getYear());
            existingVehicle.setColor(updatedVehicle.getColor());
            existingVehicle.setCustomer(updatedVehicle.getCustomer());
            return vehicleRepository.save(existingVehicle);
        } else {
            throw new ResourceNotFoundException("Veículo não encontrado com id " + id);
        }
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
