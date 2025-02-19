package com.rishi.gama;

public class User {
    private String name;
    private String userId;
    private String phoneNumber;
    private String email;
    private String password; // Note: This is not recommended for security reasons
    private String status;

    // Required empty constructor for Firebase
    public User() {
    }

    public User(String userId, String name, String phoneNumber, String email, String password, String status) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
