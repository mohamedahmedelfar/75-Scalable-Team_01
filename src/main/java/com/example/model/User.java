package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class User {

    private UUID id;
    private String name;
    private List<Order> orders = new ArrayList<>();
 
    public User(){
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID newId){
        this.id=newId;
    }

    public List<Order> getOrders(){
        return this.orders;
    }

    public void setOrders(List<Order> newOrders){
        this.orders = newOrders;
    }

    public String getUserName(){
        return this.name;
    }

    public void addOrder(Order newOrder){
        orders.add(newOrder);
    }

    public void removeOrder(UUID orderId){
        orders.removeIf(order -> order.getId().equals(orderId));
    }

}
