package com.example.model;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Order {
    private UUID orderId;

    public Order(){

    }
    
    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
