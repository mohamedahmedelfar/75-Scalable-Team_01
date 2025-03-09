package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.model.Order;
import com.example.model.User;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User>{

    public UserRepository() {

    }

    @Override
    protected String getDataPath() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDataPath'");
    }

    @Override
    protected Class<User[]> getArrayType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getArrayType'");
    }


    public ArrayList<User> getUsers(){
        return findAll();
    }


    public User getUserById(UUID userId){
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user; // Return the user if the ID matches
            }
        }
        return null;
    }

    public User addUser(User user){
        save(user);
        return user;
    }

    public List<Order> getOrdersByUserId(UUID userId){
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user.getOrders();
            }
        }
        return null;
    }

    public void addOrderToUser(UUID userId, Order order){
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getUserId().equals(userId)){
                    user.addOrder(order);
            }
        }

    }

    public void removeOrderFromUser(UUID userId, UUID orderId){
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getUserId().equals(userId)){
                List<Order> orders = user.getOrders();
                for(Order order : orders){
                    if(order.getId().equals(orderId)){
                        user.removeOrder(orderId);
                    }
                }
            }
        }
    }

    public void deleteUserById(UUID userId){
        ArrayList<User> users = findAll();
        User user1 = null;
        for (User user : users) {
            if (user.getUserId().equals(userId)){
                users.remove(user1);
                saveAll(users);
            }
        }
    }

}
