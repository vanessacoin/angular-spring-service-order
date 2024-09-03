package com.vanessa.serviceorder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanessa.serviceorder.entities.ServiceOrder;


@Repository
public interface OrderRepository extends JpaRepository<ServiceOrder, Long> {

    
}
