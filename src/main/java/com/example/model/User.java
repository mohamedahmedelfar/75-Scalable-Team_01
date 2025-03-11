package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public User(UUID id, String name, List<Order> orders){
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public User(String name, List<Order> orders){
        this.name = name;
        this.orders = orders;
    }

    public String getName() {
        return this.name;
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

    public void addOrder(Order newOrder){
        orders.add(newOrder);
    }

    public void removeOrder(UUID orderId){
        orders.removeIf(order -> order.getId().equals(orderId));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id); // Compare by UUID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
