package com.ClassNote.blank_app.data;

import java.util.List;

public class SchoolClass {

    private String name;
    private String id;
    private List<String> members;
    private String teacher;
    private String school;
    private String type;
    private String description;
    private List<ThreadClass> threads;
    private String period;

    public SchoolClass(String name, String school, String teacher, String period, String type, String id, String description, List<String> members, List<ThreadClass> threads){
        this.members = members;
        this.threads = threads;
        this.name = name;
        this.teacher = teacher;
        this.period = period;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<ThreadClass> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadClass> threads) {
        this.threads = threads;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
