package com.shopping.inventoryservice.service;

import com.shopping.inventoryservice.entity.Inventory;
import com.shopping.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean getBySkuCode(String skuCode){

        Optional<Inventory> inventory = inventoryRepository.findBySkuCode(skuCode);
        return inventory.isPresent();
    }
}
