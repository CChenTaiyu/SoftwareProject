package com.example.myapplication.entity;

public class User {
    private int id;
    private String username;
    private String password;
    private String phone;
    private String gender;
    private float height;
    private float weight;

    public User() {
    }

    public User(int id, String username, String password, String phone, String gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.height = 0;
        this.weight = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }
}
