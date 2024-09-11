package com.vanessa.serviceorder.controllers;

import com.vanessa.serviceorder.entities.Customer;
import com.vanessa.serviceorder.repositories.CustomerRepository;
import com.vanessa.serviceorder.services.CustomerService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> list() {
        return customerRepository.findAll();
    }
}
