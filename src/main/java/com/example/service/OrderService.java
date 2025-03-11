package com.example.service;

import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService extends MainService<Order> {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) {
        if (order.getTotalPrice() < 0) {
            throw new IllegalStateException("Cannot place an order with a negative price value");
        }
        orderRepository.addOrder(order);
    }

    public ArrayList<Order> getOrders() {
        return orderRepository.getOrders();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public void deleteOrderById(UUID orderId) {

        if (orderId == null) {
            throw new IllegalArgumentException("Cannot search for null Id");

        }

        orderRepository.deleteOrderById(orderId);
    }
}
