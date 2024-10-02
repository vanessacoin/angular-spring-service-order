package com.vanessa.services;

import org.springframework.stereotype.Service;

import java.util.List;

import com.vanessa.entities.Customer;
import com.vanessa.repositories.CustomerRepository;

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

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        existingCustomer.setName(customer.getName());
        existingCustomer.setCpf(customer.getCpf());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setStatus(customer.getStatus());

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
