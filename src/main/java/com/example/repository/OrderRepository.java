package com.example.repository;

import com.example.model.Order;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {

    public OrderRepository() { }

    public void addOrder(Order order) {
        List<Order> orders = getObjects("orders.json", Order.class);
        orders.add(order);
        saveObjects("orders.json", orders);
    }

    public ArrayList<Order> getOrders() {
        return getObjects("orders.json", Order.class);
    }

    public Order getOrderById(UUID orderId) {
        return getOrders().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    public void deleteOrderById(UUID orderId) {
        List<Order> orders = getOrders();
        orders.removeIf(order -> order.getId().equals(orderId));
        saveObjects("orders.json", orders);
    }
}
