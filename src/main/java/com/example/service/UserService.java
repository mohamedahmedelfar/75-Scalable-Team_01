package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
@SuppressWarnings("rawtypes")
public class UserService extends MainService<User> {

    @Autowired
    public UserService(){
       
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    
    public User addUser(User user){
        if (user.getName() == null) {
            throw new IllegalArgumentException("User name cannot be null");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

//        if (user.getName().matches(".*\\d.*")) {
//            throw new IllegalArgumentException("User name cannot contain numbers");
//        }
        return userRepository.addUser(user);
    } 

    public ArrayList<User> getUsers(){
        return userRepository.getUsers();
    }

    public User getUserById(UUID userId){
        return userRepository.getUserById(userId);
    }
    //test
    public List<Order> getOrdersByUserId(UUID userId){
        return userRepository.getOrdersByUserId(userId);
    }

    public void emptyCart(UUID userId){
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null || cart.getProducts().isEmpty()) {
            return;
        }
        cartService.deleteCartById(cart.getId());
        cart = new Cart(cart.getId(), userId, new ArrayList<Product>());
        cartService.addCart(cart);
    }

    public void addOrderToUser(UUID userId){
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null || cart.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot place an order with an empty cart");
        }
        double totalPrice=0;
        ArrayList<Product> products = cart.getProducts();
        for(Product product:products){
            totalPrice+= product.getPrice();
        }
        Order newOrder = new Order(userId,products,totalPrice);
//        orderService.addOrder(newOrder);
//        userRepository.addOrderToUser(userId, newOrder);
        User user = getUserById(userId);
        user.addOrder(newOrder);
        deleteUserById(userId);
        addUser(user);
        emptyCart(userId);
    }

    public void removeOrderFromUser(UUID userId, UUID orderId){
        userRepository.removeOrderFromUser(userId, orderId);
    }
    // tested
    public void deleteUserById(UUID userId){
        if (userId == null) {
            throw new IllegalArgumentException("Cannot search for null Id");
        }
         userRepository.deleteUserById(userId);
    }


}
