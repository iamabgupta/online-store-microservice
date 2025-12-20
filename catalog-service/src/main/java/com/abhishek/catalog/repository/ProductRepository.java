package com.abhishek.catalog.repository;

import com.abhishek.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is a data access layer for product entity.
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
