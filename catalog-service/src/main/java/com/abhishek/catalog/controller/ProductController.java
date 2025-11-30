package com.abhishek.catalog.controller;

import com.abhishek.catalog.model.Product;
import com.abhishek.catalog.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list")
    public List<Product> getAllProducts(){
       return repository.findAll();
    }
}
