package com.ClassNote.blank_app;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {

    public static final int FAILED_CREDENTIALS = -1;
    public static final int UNCONFIRMED_CREDENTIALS = 0;
    public static final int CONFIRMED_CREDENTIALS = 1;

    private String id;
    private String name;
    private String birthDate;
    private String description;
    private String type;
    private String email;
    private String onboard;
    private String username;
    private int credentials;

    // TODO : add newUser boolean to only ask to update user info once per launch
    // determines is newUser if description or birthdate are null


    public User(String username, String password){
        JSONObject response = confirmCredentials(username, password);
        if(!response.toString().contains("none")){
            this.username = username;
            id = fetchID(response);
            name = fetchName(response);
            birthDate = fetchBirthDate(response);
            description = fetchDescription(response);
            type = fetchType(response);
            email = fetchEmail(response);
            onboard = fetchOnboard(response);
            credentials = CONFIRMED_CREDENTIALS;
        } else {
            credentials = FAILED_CREDENTIALS;
        }
    }

    public void logout(){
        this.username = null;
        id = null;
        name = null;
        birthDate = null;
        description = null;
        type = null;
        email = null;
        onboard = null;
    }

    /**
     * @param username
     * @param password
     * @return Null if credentials are wrong, MySQL cursor object if they are correct.
     */

    // TODO : build activity for updating user / pass
    private JSONObject confirmCredentials(String username, String password){
        ConnectMySQL c = new ConnectMySQL();
        try {
            String jsonUserString = c.getUser(username, password);
            System.out.println(jsonUserString);
            JSONObject jsonUserObject = new JSONObject(jsonUserString);
            return jsonUserObject;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String fetchName(JSONObject cursor){
        try {
            return cursor.getString("name");
        } catch(Exception e) {
            return " ";
        }

    }

    private String fetchID(JSONObject cursor){
        try {
            return cursor.getString("id");
        } catch(Exception e) {
            return " ";
        }
    }

    private String fetchBirthDate(JSONObject cursor){
        try {
            return cursor.getString("birth");
        } catch(Exception e) {
            return " ";
        }
    }

    private String fetchDescription(JSONObject cursor){
        try {
            return cursor.getString("description");
        } catch(Exception e) {
            return " ";
        }
    }

    private String fetchType(JSONObject cursor){
        try {
            return cursor.getString("type");
        } catch(Exception e) {
            return " ";
        }
    }

    private String fetchEmail(JSONObject cursor){
        try {
            return cursor.getString("email");
        } catch(Exception e) {
            return " ";
        }
    }

    private String fetchOnboard(JSONObject cursor){
        try {
            return cursor.getString("onboard");
        } catch(Exception e) {
            return " ";
        }
    }

    public List<SchoolClass> fetchClasses(){
        ArrayList<SchoolClass> classes = new ArrayList<>();
        //classes.add()
        // TODO : iterate over fetched json file and add each class to classes
        return classes;
    }

    // START OF GETTERS & SETTERS

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOnboard() {
        return onboard;
    }

    public void setOnboard(String onboard) {
        this.onboard = onboard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCredentials() {
        return credentials;
    }

    public void setCredentials(int credentials) {
        this.credentials = credentials;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        String[] stringPackage = {id, name, birthDate, description, type, email, onboard, username};
        parcel.writeStringArray(stringPackage);
    }

    // Borrowed from https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in){
        String[] stringPackage = new String[8];
        in.readStringArray(stringPackage);
        id = stringPackage[0];
        name = stringPackage[1];
        birthDate = stringPackage[2];
        description = stringPackage[3];
        type = stringPackage[4];
        email = stringPackage[5];
        onboard = stringPackage[6];
        username = stringPackage[7];
    }
}
