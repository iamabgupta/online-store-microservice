package com.abhishek.catalog.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "Product name can not be empty")
    private String name;

    @DecimalMin(value = "1.00", message = "Price must be greater than or equal to 1.00")
    private BigDecimal price;

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}