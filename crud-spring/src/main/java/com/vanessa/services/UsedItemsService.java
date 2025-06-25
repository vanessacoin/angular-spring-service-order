package com.vanessa.services;

import com.vanessa.repositories.UsedItemsRepository;
import org.springframework.stereotype.Service;

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
