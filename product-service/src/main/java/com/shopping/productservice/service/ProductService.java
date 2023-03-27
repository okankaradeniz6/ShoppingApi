package com.shopping.productservice.service;

import com.shopping.productservice.dto.ProductRequest;
import com.shopping.productservice.dto.ProductResponse;
import com.shopping.productservice.entity.Product;
import com.shopping.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private ModelMapper modelMapper;

    public void createProduct(ProductRequest request) {
        //Product product = Product.builder()
        //        .name(request.getName())
        //        .description(request.getDescription())
        //        .price(request.getPrice())
        //        .build();

        Product product = modelMapper.map(request, Product.class);
        productRepository.save(product);
        log.info("Product {} is saved", product);
    }

    public List<ProductResponse> getAllProducts(){

        List<Product> products = productRepository.findAll();
        log.info("Products: {}", products);

        return products.stream().map(
                (product)-> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());

    }

}
