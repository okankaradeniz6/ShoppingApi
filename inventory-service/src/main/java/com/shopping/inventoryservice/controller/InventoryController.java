package com.shopping.inventoryservice.controller;

import com.shopping.inventoryservice.repository.InventoryRepository;
import com.shopping.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping("{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode){

        return inventoryService.getBySkuCode(skuCode);
    }
}
