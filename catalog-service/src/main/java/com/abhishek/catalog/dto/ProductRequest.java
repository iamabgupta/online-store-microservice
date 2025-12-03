package com.abhishek.catalog.dto;

import java.math.BigDecimal;

public class ProductRequest {
    private String name;
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