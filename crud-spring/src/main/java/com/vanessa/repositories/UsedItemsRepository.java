package com.vanessa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanessa.entities.UsedItems;

@Repository
public interface UsedItemsRepository extends JpaRepository<UsedItems, Long> {
    
}
