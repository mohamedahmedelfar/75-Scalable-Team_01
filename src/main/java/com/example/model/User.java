package com.example.model;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;

    // Constructor (Optional)
    public User() {}

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
