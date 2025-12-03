package com.abhishek.catalog.controller;

import com.abhishek.catalog.dto.ProductRequest;
import com.abhishek.catalog.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController{
        private final com.abhishek.catalog.service.ProductService productService;

        public ProductController(com.abhishek.catalog.service.ProductService productService) {
            this.productService = productService;
        }

        @PostMapping("/create")
        public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
            return ResponseEntity.ok(productService.createProduct(request));
        }

        @GetMapping
        public ResponseEntity<List<ProductResponse>> getAll() {
            return ResponseEntity.ok(productService.getAllProducts());
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
            return ResponseEntity.ok(productService.getProductById(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ProductResponse> update(
                @PathVariable Long id,
                @RequestBody ProductRequest request) {

            return ResponseEntity.ok(productService.updateProduct(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
}
