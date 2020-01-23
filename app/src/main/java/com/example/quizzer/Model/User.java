package com.example.quizzer.Model;

public class User {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String token;

    public User(){}

    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
