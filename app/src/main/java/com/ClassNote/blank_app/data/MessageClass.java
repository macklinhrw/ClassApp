package com.ClassNote.blank_app.data;

import java.util.TimeZone;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MessageClass {

    private String id;
    private String sender;
    private String thread;
    private String utc_datetime;
    private String local_datetime;
    private String text;
    private String author;

    public MessageClass(String id, String sender, String thread, String utc_datetime, String text, String author) {
        this.id = id;
        this.sender = sender;
        this.thread = thread;
        this.utc_datetime = utc_datetime;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(utc_datetime);
            df.setTimeZone(TimeZone.getDefault());
            this.local_datetime = df.format(date);
        } catch(Exception e) {
            this.local_datetime = utc_datetime;
        }
        this.text = text;
        this.author = author;
    }

    public String getCurrentTimeZone(){
        TimeZone tz = Calendar.getInstance().getTimeZone();
        System.out.println(tz.getDisplayName());
        return tz.getID();
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getThread() {
        return thread;
    }

    public String getUtc_datetime() {
        return utc_datetime;
    }

    public String getLocal_datetime() {
        return local_datetime;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }
}