package com.vanessa.serviceorder.services;

import com.vanessa.serviceorder.entities.Customer;
import com.vanessa.serviceorder.repositories.CustomerRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    
    private CustomerRepository customerRepository;
  
    public List<Customer> getAllCustomers() {
      return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
      Optional<Customer> obj = customerRepository.findById(id);
      return obj.get();
    }
}
