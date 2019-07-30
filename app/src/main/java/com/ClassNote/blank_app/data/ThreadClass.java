package com.ClassNote.blank_app.data;

import java.util.List;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.ArrayList;

public class ThreadClass {

    private String name;
    private String id;
    private List<String> members;
    private String school;
    private String type;
    private String description;
    private String in_class;
    private String last_message_dt;


    public ThreadClass(String in_class, String school, String group_name, String type, String id, String description, List<String> members){
        this.members = members;
        this.in_class = in_class;
        this.name = group_name;
        this.type = type;
        this.school = school;
        this.description = description;
        // TODO make sure ALL timestamps are on UTC for server side and back end, then translate for display
        this.id = id;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d=new Date();
        this.last_message_dt = df.format(d);
    }

    // gets last 50 messages sent
    public ArrayList<MessageClass> fetchMessages() {
        ConnectMySQL2 c = new ConnectMySQL2();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d=new Date();
        this.last_message_dt = df.format(d);
        return c.fetchMessages(this.id);
    }

    // gets messages that have been sent since last checked
    public ArrayList<MessageClass> fetchNewMessages() {
        ConnectMySQL2 c = new ConnectMySQL2();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d=new Date();
        this.last_message_dt = df.format(d);
        return c.fetchNewMessages(this.id, this.last_message_dt);
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

    public String getLast_message_dt() {
        return last_message_dt;
    }

    public void setLast_message_dt(String last_message_dt) {
        this.last_message_dt = last_message_dt;
    }
}
