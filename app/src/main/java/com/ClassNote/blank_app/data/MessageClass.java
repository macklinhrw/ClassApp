package com.ClassNote.blank_app.data;

import com.ClassNote.blank_app.utilities.FormattedDate;

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
        FormattedDate d = new FormattedDate(utc_datetime);
        this.local_datetime = d.convertToLocal();
        this.text = text;
        this.author = author;
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