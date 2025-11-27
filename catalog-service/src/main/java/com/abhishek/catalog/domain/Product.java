package com.abhishek.catalog.domain;

public class Product {

    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean available;

    public Product() {
    }

    public Product(Long id, String name, String description, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public Product setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}