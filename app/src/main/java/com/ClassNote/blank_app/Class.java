package com.ClassNote.blank_app;

import java.util.ArrayList;

public class Class {

    private String name;
    private String id;
    // can make a teacher object if necessary
    private String teacher;
    private ArrayList<String> threads;
    private int period;

    public Class(String name, String teacher, int period){
        this.name = name;
        this.teacher = teacher;
        this.period = period;
        // TODO : auto gen code for ID
        id = "Insert_Class_ID";
        threads.add("Insert_General_ID_Thread");
    }

    public Class(String name, String teacher, int period, String id){
        this.name = name;
        this.teacher = teacher;
        this.period = period;
        this.id = id;
        threads.add("Insert_General_ID_Thread");
    }

    // START GETTERS & SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public ArrayList<String> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<String> threads) {
        this.threads = threads;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
