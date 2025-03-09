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

    boolean userFound=false;

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
        if(users==null) {
            return null;
        }
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user; // Return the user if the ID matches
            }
        }
        return null;
    }

    public User addUser(User user){
        if(user==null) {
            return null;
        }
        save(user);
        return user;
    }

    public List<Order> getOrdersByUserId(UUID userId){
        ArrayList<User> users = findAll();
        if(users==null) {
            return null;
        }
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user.getOrders();
            }
        }
        return null;
    }

    public void addOrderToUser(UUID userId, Order order){
        ArrayList<User> users = findAll();
        if(users==null) {
            return;
        }
        for (User user : users) {
            if (user.getId().equals(userId)){
                    user.addOrder(order);
            }
        }

    }

    public void removeOrderFromUser(UUID userId, UUID orderId){
        ArrayList<User> users = findAll();
        if(users==null) {
            return;
        }
        for (User user : users) {
            if (user.getId().equals(userId)){
                List<Order> orders = user.getOrders();
                orders.removeIf(order -> order.getId().equals(orderId));
                save(user);
                return;
            }
        }
    }

    public void deleteUserById(UUID userId){
        ArrayList<User> users = findAll();
        if(users==null) {
            return;
        }
        for (User user : users) {
            if (user.getId().equals(userId)){
                userFound=true;
            }
        }
        if(!userFound) {
            return;
        }
        users.removeIf(user -> user.getId().equals(userId));
        //saveAll(users);
    }

}
