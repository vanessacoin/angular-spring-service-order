package com.vanessa.services;

import org.springframework.stereotype.Service;
import java.util.List;
import com.vanessa.dto.CustomerRequestDTO;
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
      .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
  }

  public Customer saveCustomer(CustomerRequestDTO dto) {
    Customer customer = new Customer();
    customer.setName(dto.getName());
    customer.setCpf(blankToNull(dto.getCpf()));
    customer.setPhone(blankToNull(dto.getPhone()));
    customer.setEmail(blankToNull(dto.getEmail()));

    customer.setStatus(true);

    return customerRepository.save(customer);
  }

  public Customer updateCustomer(Long id, CustomerRequestDTO dto) {
    Customer existing = customerRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));

    existing.setName(dto.getName());
    existing.setCpf(blankToNull(dto.getCpf()));
    existing.setPhone(blankToNull(dto.getPhone()));
    existing.setEmail(blankToNull(dto.getEmail()));

    return customerRepository.save(existing);
  }

  public void deleteCustomer(Long id) {
    if (!customerRepository.existsById(id)) {
      throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
    }
    customerRepository.deleteById(id);
  }

  private String blankToNull(String value) {
    if (value == null) return null;
    String v = value.trim();
    return v.isEmpty() ? null : v;
  }
}
