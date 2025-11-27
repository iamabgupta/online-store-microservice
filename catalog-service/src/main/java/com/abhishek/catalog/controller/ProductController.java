package com.abhishek.catalog.controller;

import com.abhishek.catalog.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return List.of(
                new Product(1L, "Laptop", "LightWeight developers laptop", 75000, true),
                new Product(2L, "Mechanical keyboard", "RGB keyboard", 4500, true),
                new Product(3L, "Wireless mouse", "Wireless mouse", 1500, true)
        );
    }
}
