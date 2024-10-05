package com.vanessa.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.vanessa.entities.Customer;
import com.vanessa.repositories.CustomerRepository;
import com.vanessa.resources.exceptions.ResourceNotFoundException;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOpt = customerRepository.findById(id);
        
        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            // Atualiza os campos desejados
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setCpf(updatedCustomer.getCpf());
            existingCustomer.setPhone(updatedCustomer.getPhone());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            return customerRepository.save(existingCustomer);
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
