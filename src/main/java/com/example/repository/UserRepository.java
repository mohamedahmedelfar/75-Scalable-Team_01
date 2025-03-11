package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.service.OrderService;
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
                    deleteUserById(userId);
                    addUser(user);
                    break;
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
                int index = 0;
                boolean found = false;
                for (Order order : orders){
                    if(order.getId().equals(orderId)){
                        found = true;
                        break;
                    }
                    index++;
                }
                User user1 = getUserById(userId);
                if(found)
                    user1.getOrders().remove(index);
                deleteUserById(userId);
                addUser(user1);
                //save(user);
                return;
            }
        }
    }

    public void deleteUserById(UUID userId){
        ArrayList<User> users = findAll();
        if(users==null) {
            System.out.println("no users found");
            return;
        }
        User tempUser = null;
        for (User user : users) {
            if (user.getId().equals(userId)){
                userFound=true;
                tempUser=user;
            }
        }
        if(!userFound) {
            System.out.println("no user with this id found");
            return;
        }
        users.remove(tempUser);
        saveAll(users);
    }

}
