package com.shopping.productservice.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class ProductResponse {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
