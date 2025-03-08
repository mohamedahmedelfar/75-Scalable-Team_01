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

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUserId(){
        return this.id;
    }

    public List<Order> getOrders(){
        return this.orders;
    }

    public String getUserName(){
        return this.name;
    }

    public void addOrder(Order newOrder){
        orders.add(newOrder);
    }

    public void removeOrder(UUID orderId){
        orders.remove(orderId);
    }

}
