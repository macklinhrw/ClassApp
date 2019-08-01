package com.ClassNote.blank_app.data;

import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;
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

        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsed = null;
        sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try{
            parsed = sourceFormat.parse(utc_datetime); // => Date is in UTC now
        } catch (Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        destFormat.setTimeZone(TimeZone.getTimeZone(getCurrentTimeZone()));
        this.local_datetime = destFormat.format(parsed);
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