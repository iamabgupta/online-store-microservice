package com.abhishek.catalog.service;

import com.abhishek.catalog.dto.ProductRequest;
import com.abhishek.catalog.dto.ProductResponse;
import com.abhishek.catalog.exception.ProductNotFoundException;
import com.abhishek.catalog.model.Product;
import com.abhishek.catalog.repository.ProductRepository;
import com.abhishek.catalog.services.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_productExists() {
        Product product = new Product(1L, "Test Product", BigDecimal.valueOf(100));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Test Product");
    }

    @Test
    void testGetById_productNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = new ProductRequest("Phone", BigDecimal.valueOf(500));
        Product savedProduct = new Product(1L, "Phone", BigDecimal.valueOf(500));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Phone");
    }

    @Test
    void testUpdateProduct() {
        Product existing = new Product(1L, "Laptop", BigDecimal.valueOf(1000));
        ProductRequest request = new ProductRequest("Laptop Updated", BigDecimal.valueOf(1500));

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        ProductResponse response = productService.updateProduct(1L, request);

        assertThat(response.getName()).isEqualTo("Laptop Updated");
        assertThat(response.getPrice()).isEqualTo(BigDecimal.valueOf(1500));
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}
