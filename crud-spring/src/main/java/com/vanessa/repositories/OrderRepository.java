package com.vanessa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanessa.entities.ServiceOrder;

@Repository
public interface OrderRepository extends JpaRepository<ServiceOrder, Long> {
    
}
