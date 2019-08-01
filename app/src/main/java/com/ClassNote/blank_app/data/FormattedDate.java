package com.ClassNote.blank_app.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FormattedDate {

    private String formattedString;
    private Calendar calendar;

    public FormattedDate(String utc_time){
        formattedString = utc_time;
        calendar = Calendar.getInstance();
    }

    public FormattedDate(){
        formattedString = "";
        calendar = Calendar.getInstance();
    }

    public String createUTC(){
        Date d = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(getUTC());
        return dateFormat.format(d);
    }

    public void resetCalendar(){
        calendar = Calendar.getInstance();
    }

    public String convertToLocal(String utc_datetime){
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsed = null;
        sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try{
            parsed = sourceFormat.parse(utc_datetime); // => Date is in UTC now
        } catch (Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        destFormat.setTimeZone(getLocalTimeZone());
        return destFormat.format(parsed);
    }

    public String convertToLocal(){
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsed = null;
        sourceFormat.setTimeZone(getUTC());
        try{
            parsed = sourceFormat.parse(formattedString); // => Date is in UTC now
        } catch (Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        destFormat.setTimeZone(getLocalTimeZone());
        return destFormat.format(parsed);
    }

    public TimeZone getLocalTimeZone(){
        return calendar.getTimeZone();
    }

    public TimeZone getUTC(){
        return TimeZone.getTimeZone("UTC");
    }
}
