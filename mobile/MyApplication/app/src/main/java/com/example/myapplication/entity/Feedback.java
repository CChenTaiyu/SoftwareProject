package com.example.myapplication.entity;

public class Feedback {
    private int id;
    private String content;

    public Feedback() {

    }

    public Feedback(int id, String content) {
        super();
        this.content = content;
        this.id = id;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String get_content() {
        return content;
    }

    public void set_content(String content) {
        this.content = content;
    }
}