package com.vanessa.services;

import org.springframework.stereotype.Service;

import com.vanessa.repositories.UsedItemsRepository;

@Service
public class UsedItemsService {

    private final UsedItemsRepository usedItemsRepository;

    public UsedItemsService(UsedItemsRepository usedItemsRepository) {
        this.usedItemsRepository = usedItemsRepository;
    }

    public void deleteById(long id) {
        usedItemsRepository.deleteById(id);
    }
}
