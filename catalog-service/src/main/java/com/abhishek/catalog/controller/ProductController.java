package com.abhishek.catalog.controller;

import com.abhishek.catalog.dto.ProductRequest;
import com.abhishek.catalog.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is REST API class.
 *
 */

/**
 * @RestController Marks this class as REST API and returns values to JSON Automatically
 * @RequestMapping Sets base URL for all endpoints
 */
@RestController
@RequestMapping("/products")
public class ProductController{

        //This decouples constructor from implementation(Service class where we implement business logic)
        private final com.abhishek.catalog.service.ProductService productService;

        //Constructor injection : Here Product service is given by spring automatically.
        public ProductController(com.abhishek.catalog.service.ProductService productService) {
            this.productService = productService;
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/create")
        public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
            //Spring sends response of creating object in jason format
            return ResponseEntity.ok(productService.createProduct(request));
        }


        @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'READ_ONLY')")
        @GetMapping("/list")
        public ResponseEntity<List<ProductResponse>> getAll() {
            //Springs converts List<ProductResponse> --> JSON response
            return ResponseEntity.ok(productService.getAllProducts());
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'READ_ONLY')")
        @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
            return ResponseEntity.ok(productService.getProductById(id));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}")
        public ResponseEntity<ProductResponse> update(
                @PathVariable Long id,
                @Valid @RequestBody ProductRequest request) {

            return ResponseEntity.ok(productService.updateProduct(id, request));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
}
