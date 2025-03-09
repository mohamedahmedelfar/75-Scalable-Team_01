package com.example.model;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Order {

    private UUID id;
    private UUID userId;
    private double totalPrice;
    private List<Product> products = new ArrayList<>();

    // Constructors
    public Order(UUID id, UUID userId, double totalPrice, List<Product> products) {
        this.id = id;
        this.totalPrice=totalPrice;
        this.userId=userId;
        this.products=products;
    }

    public Order(UUID userId, List<Product> products, double totalPrice) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
