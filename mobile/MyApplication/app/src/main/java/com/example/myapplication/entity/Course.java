package com.example.myapplication.entity;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Course {
    private String name;
    private Date date;
    private int id;
    private int capacity;
    private int applicants;
    private int count;
    private String capetory;
    private int duration;
    private String description;

    public Course(int id, String name, Date date, int capacity, int applicants, int count, String capetory, int duration, String description) {
        super();
        this.id = id;
        this.name = name;
        this.date = date;
        this.capacity = capacity;
        this.applicants = applicants;
        this.count = count;
        this.capetory = capetory;
        this.duration = duration;
        this.description = description;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public Date get_date() {
        return date;
    }

    public void set_date(Date date) {
        this.date = date;
    }

    public int get_capacity() {
        return capacity;
    }

    public void set_capacity(int capacity) {
        this.capacity = capacity;
    }

    public int get_applicants() {
        return applicants;
    }

    public void set_applicants(int applicants) {
        this.applicants = applicants;
    }

    public int get_count() {
        return count;
    }

    public void set_count(int count) {
        this.count = count;
    }

    public String get_capetory() {
        return capetory;
    }

    public void set_capetory(String capetory) {
        this.capetory = capetory;
    }

    public int get_duration() {
        return duration;
    }

    public void set_duration(int duration) {
        this.duration = duration;
    }

    public String get_description() {
        return description;
    }

    public void set_description(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String format = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return " " + name + ", \n" + formatter.format(date);
    }

}
