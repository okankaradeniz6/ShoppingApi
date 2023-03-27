package com.shopping.productservice.controller;

import com.shopping.productservice.dto.ProductRequest;
import com.shopping.productservice.dto.ProductResponse;
import com.shopping.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping("create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest request){

        productService.createProduct(request);
    }

    @GetMapping("get")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
