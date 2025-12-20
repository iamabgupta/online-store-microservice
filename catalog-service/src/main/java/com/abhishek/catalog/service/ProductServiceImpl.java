package com.abhishek.catalog.services;

import com.abhishek.catalog.dto.ProductRequest;
import com.abhishek.catalog.dto.ProductResponse;
import com.abhishek.catalog.exception.ProductNotFoundException;
import com.abhishek.catalog.mapper.ProductMapper;
import com.abhishek.catalog.model.Product;
import com.abhishek.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements com.abhishek.catalog.services.ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product= productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
        return  ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
