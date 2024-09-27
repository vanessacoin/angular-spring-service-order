package com.vanessa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanessa.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
