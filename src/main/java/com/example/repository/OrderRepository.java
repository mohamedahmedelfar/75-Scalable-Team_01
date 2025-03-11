package com.example.repository;

import com.example.model.Order;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/orders.json";
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }

    public OrderRepository() { }

    public void addOrder(Order order) {
        save(order);
    }

    public ArrayList<Order> getOrders() {
        return findAll();
    }

    public Order getOrderById(UUID orderId) {
        return getOrders().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = getOrders();
        int index = 0;
        boolean found = false;
        for(Order order : orders) {
            if(order.getId().equals(orderId)) {
                found = true;
                break;
            }
            index++;
        }
        if(found)
            orders.remove(index);
        saveAll(orders);
    }
}
