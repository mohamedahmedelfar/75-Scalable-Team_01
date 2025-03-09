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
        return "src/main/java/com/example/data/users.json";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }


    public ArrayList<User> getUsers(){
        return findAll();
    }


    public User getUserById(UUID userId){
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(userId)) {
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
            if (user.getId().equals(userId)) {
                return user.getOrders();
            }
        }
        return null;
    }

    public void addOrderToUser(UUID userId, Order order){
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(userId)){
                    user.addOrder(order);
            }
        }

    }

    public void removeOrderFromUser(UUID userId, UUID orderId){
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(userId)){
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
        users.removeIf(user -> user.getId().equals(userId));
        saveAll(users);
    }

}
