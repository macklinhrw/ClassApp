package com.ClassNote.blank_app;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import android.os.AsyncTask;

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
        return d.doInBackground(username, password, name, email);
    }
}
