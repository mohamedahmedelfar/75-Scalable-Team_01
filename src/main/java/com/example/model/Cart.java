package com.example.model;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class Cart {
    private UUID id;
    private UUID userId;
    private List<Product> products=new ArrayList<>();
    public Cart() {
        this.id = UUID.randomUUID();
    }
    public Cart(UUID userId,ArrayList<Product> products) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.products = products;
    }
    public Cart(UUID id,UUID userId,ArrayList<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public ArrayList<Product> getProducts() {
        return (ArrayList<Product>) products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String toString(){
        return "Cart id: "+this.id+" User id: "+this.userId+" Products: "+this.products;
    }
}
