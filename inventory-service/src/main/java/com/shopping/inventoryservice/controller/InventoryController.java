package com.shopping.inventoryservice.controller;

import com.shopping.inventoryservice.dto.InventoryResponse;
import com.shopping.inventoryservice.repository.InventoryRepository;
import com.shopping.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){

        return inventoryService.getBySkuCode(skuCode);
    }
}
