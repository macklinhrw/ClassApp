package com.ClassNote.blank_app;

import java.util.ArrayList;
import java.util.List;

import com.ClassNote.blank_app.Enums.Type;

public class ThreadClass {

    private String name;
    private String id;
    private List<String> members;
    private String school;
    private String type;
    private String description;
    private String in_class;


    public ThreadClass(String in_class, String school, String group_name, String type, String id, String description, List<String> members){
        this.members = members;
        this.in_class = in_class;
        this.name = group_name;
        this.type = type;
        this.school = school;
        this.description = description;
        this.id = id;
    }


    // TODO : Overload constructor?

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

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIn_class() {
        return in_class;
    }

    public void setIn_class(String in_class) {
        this.in_class = in_class;
    }
}
