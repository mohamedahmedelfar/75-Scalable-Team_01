package com.example.repository;

import com.example.model.User;

import java.util.ArrayList;

public class UserRepository extends MainRepository<User>{
    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/users.json";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }



    // Define getUsers() explicitly if needed
    public ArrayList<User> getUsers() {
        return findAll();
    }
}
