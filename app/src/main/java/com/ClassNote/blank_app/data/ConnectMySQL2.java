package com.ClassNote.blank_app.data;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import android.os.AsyncTask;
import org.json.JSONObject;

public class ConnectMySQL2 {
    public ArrayList<MessageClass> fetchMessages(String thread_id) {

        class DownloadJSON extends AsyncTask<String, Void, ArrayList<MessageClass>> {

            public DownloadJSON() {
                super();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<MessageClass> doInBackground(String... strings) {
                try{
                    ArrayList<String> messageStrings = new ArrayList<>();
                    ArrayList<MessageClass> messages = new ArrayList<>();
                    URL url = new URL("http://classnoteutil.000webhostapp.com/fetch_messages.php?thread="+strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = rd.readLine();
                    while (response.contains("{")){
                        messageStrings.add(response.substring(response.indexOf("{"), response.indexOf("}") + 1));
                        response = response.substring(response.indexOf("}") + 1);
                    }
                    for(String curClass : messageStrings){
                        JSONObject json = new JSONObject(curClass);
                        System.out.println(json);
                        messages.add(new MessageClass(json.getString("id"), json.getString("sender"),
                                json.getString("thread"), json.getString("datetime"), json.getString("text"),
                                json.getString("author")));
                    }
                    return messages;
                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<MessageClass> s) {
                super.onPostExecute(s);
                try {
                    System.out.println(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        DownloadJSON d = new DownloadJSON();
        return d.doInBackground(thread_id);
    }

    public ArrayList<MessageClass> fetchNewMessages(String thread_id, String timestamp) {

        class DownloadJSON extends AsyncTask<String, Void, ArrayList<MessageClass>> {

            public DownloadJSON() {
                super();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<MessageClass> doInBackground(String... strings) {
                try{
                    ArrayList<String> messageStrings = new ArrayList<>();
                    ArrayList<MessageClass> messages = new ArrayList<>();
                    URL url = new URL("http://classnoteutil.000webhostapp.com/fetch_new_messages.php?thread="+strings[0]+"&datetime="+strings[1]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = rd.readLine();
                    while (response.contains("{")){
                        messageStrings.add(response.substring(response.indexOf("{"), response.indexOf("}") + 1));
                        response = response.substring(response.indexOf("}") + 1);
                    }
                    for(String curClass : messageStrings){
                        JSONObject json = new JSONObject(curClass);
                        System.out.println(json);
                        messages.add(new MessageClass(json.getString("id"), json.getString("sender"),
                                json.getString("thread"), json.getString("datetime"), json.getString("text"),
                                json.getString("author")));
                    }
                    return messages;
                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<MessageClass> s) {
                super.onPostExecute(s);
                try {
                    System.out.println(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        DownloadJSON d = new DownloadJSON();
        return d.doInBackground(thread_id);
    }
}
