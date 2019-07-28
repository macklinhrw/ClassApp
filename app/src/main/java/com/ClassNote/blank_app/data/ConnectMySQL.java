package com.ClassNote.blank_app.data;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.ClassNote.blank_app.data.SchoolClass;

import org.json.JSONObject;

public class ConnectMySQL{

    public String getUser(String username, String password) {

        class DownloadJSON extends AsyncTask<String, Void, String> {

            public DownloadJSON() {
                super();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url = new URL("http://classnoteutil.000webhostapp.com/users_connect.php?username="+strings[0]+"&password="+strings[1]);
                    //System.out.println(url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //System.out.println(conn);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    //System.out.println(rd);
                    String jsonline = rd.readLine();
                    System.out.println(jsonline);
                    return jsonline;
                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    System.out.println(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        DownloadJSON d = new DownloadJSON();
        //d.execute(username, password);
        return d.doInBackground(username, password);
    }

    public String newUser(String username, String password, String name, String email) {

        class DownloadJSON extends AsyncTask<String, Void, String> {

            public DownloadJSON() {
                super();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url = new URL("http://classnoteutil.000webhostapp.com/new_user.php?username="+strings[0]+"&password="+strings[1]+"&name="+strings[2]+"&email="+strings[3]);
                    //System.out.println(url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //System.out.println(conn);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    //System.out.println(rd);
                    String response = rd.readLine();
                    return response;
                } catch(Exception e) {
                    e.printStackTrace();
                    return "unknown failure";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    System.out.println(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        DownloadJSON d = new DownloadJSON();
        return d.doInBackground(username, password, name, email);
    }

    public String updateUser(String id, String email, String name, String birth, String description, String nickname) {

        class DownloadJSON extends AsyncTask<String, Void, String> {

            public DownloadJSON() {
                super();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    String query = "http://classnoteutil.000webhostapp.com/update_user.php?id="+id;
                    if(email != null) {
                        query += "&email=" + email;
                    }
                    if(name != null) {
                        query += "&name=" + name;
                    }
                    if(birth != null) {
                        query += "&birth=" + birth;
                    }
                    if(description != null) {
                        query += "&description=" + description;
                    }
                    if(nickname != null) {
                        query += "&nickname=" + nickname;
                    }
//                    System.out.println(query);
                    URL url = new URL(query);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = rd.readLine();
                    return response;
                } catch(Exception e) {
                    e.printStackTrace();
                    return "unknown failure";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    System.out.println(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        DownloadJSON d = new DownloadJSON();
        return d.doInBackground(id, email, name, birth, description, nickname);
    }

    public List<SchoolClass> getClass(String id) {

        class DownloadJSON extends AsyncTask<String, Void, List<SchoolClass>> {

            public DownloadJSON() {
                super();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<SchoolClass> doInBackground(String... strings) {
                try{
                    ArrayList<String> classStrings = new ArrayList<>();
                    ArrayList<SchoolClass> classes = new ArrayList<>();
                    URL url = new URL("http://classnoteutil.000webhostapp.com/fetch_user_classes.php?id="+strings[0]);
                    //System.out.println(url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //System.out.println(conn);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    //System.out.println(rd);
                    String response = rd.readLine();
                    while (response.contains("{")){
                        classStrings.add(response.substring(response.indexOf("{"), response.indexOf("}") + 1));
                        response = response.substring(response.indexOf("}") + 1);
                    }
                    for(String curClass : classStrings){
                        JSONObject json = new JSONObject(curClass);
                        System.out.println(json);
                        classes.add(new SchoolClass(json.getString("title"), json.getString("school"),
                                json.getString("teacher"), json.getInt("period"), json.getString("type"),
                                json.getString("id"), json.getString("description"), new ArrayList<String>(),
                                new ArrayList<String>()));
                    }
                    return classes;
                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<SchoolClass> s) {
                super.onPostExecute(s);
                try {
                    System.out.println(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        DownloadJSON d = new DownloadJSON();
        return d.doInBackground(id);
    }
}
