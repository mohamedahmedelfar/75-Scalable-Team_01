package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Order;
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

    
    public User addUser(User user){
        return userRepository.addUser(user);
    } 

    public ArrayList<User> getUsers(){
        return userRepository.getUsers();
    }

    public User getUserById(UUID userId){
        return userRepository.getUserById(userId);
    }

    /* wait for CartService 
    public List<Order> getOrdersByUserId(UUID userId){
        return userRepository.getOrdersByUserId(userId);
    }

    public void emptyCart(UUID userId){

    }
    */
    public void addOrderToUser(UUID userId){
        userRepository.addOrderToUser(userId, null);
    }

    public void removeOrderFromUser(UUID userId, UUID orderId){
        userRepository.removeOrderFromUser(userId, orderId);
    }

    public void deleteUserById(UUID userId){
        userRepository.deleteUserById(userId);
    }


}
