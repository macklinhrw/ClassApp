package com.ClassNote.blank_app.Enums;

public enum Type {
    GENERAL("general"),
    PHYSICS("physics"),
    MATH("math"),
    ENGLISH("english");

    private String name;

    Type(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
