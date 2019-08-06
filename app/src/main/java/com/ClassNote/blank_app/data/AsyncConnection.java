package com.ClassNote.blank_app.data;

import android.os.AsyncTask;
import android.os.Handler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncConnection {

    private Connectable c;
    private String[] args;
    private String[] params;
    private String path;
    private boolean repeatable;
    private int timer;
    private Timeable t;

    public AsyncConnection(Connectable conn, String path, String[] args, String[] params){
        c = conn;
        this.args = args;
        this.params = params;
        this.path = path;
        repeatable = false;
    }

    public AsyncConnection(Connectable conn, String path, String[] args, String[] params, Timeable t, int timer){
        c = conn;
        this.args = args;
        this.params = params;
        this.path = path;
        repeatable = true;
        this.timer = timer;
        this.t = t;
    }

    public void start(){
        AsyncFetch a = new AsyncFetch();
        //is this necessary? is it bad to access the instance variables from the subclass if it's an async task?
        String[] info = {path};
        a.execute(info, args, params);
    }

    public void updateValues(List<List<String>> objectValues){
        c.updateData(objectValues);
        if (repeatable) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AsyncFetch asy = new AsyncFetch();
                    if(objectValues != null){
//                        String newTime = result.get(result.size() - 1).getUtc_datetime();
//                        time = newTime;
                        String[] info = {path, t.getTime()};
                        asy.execute(info, args, params);
                    } else {
                        String[] info = {path};
                        asy.execute(info, args, params);
                    }
                }
            }, timer);
            c.updateData(objectValues);
        }
    }

    class AsyncFetch extends AsyncTask<String[], Void, List<List<String>>>{

        @Override
        protected List<List<String>> doInBackground(String[]... strings) {
            try{
                //Log.i("msg", "in async task");
                ArrayList<String> responseStrings = new ArrayList<>();
                ArrayList<List<String>> processedData = new ArrayList<>();
                URL url = new URL(strings[0][0] + "?" + compilePath(strings[1], strings[2]));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = rd.readLine();
                while (response.contains("{")){
                    responseStrings.add(response.substring(response.indexOf("{"), response.indexOf("}") + 1));
                    response = response.substring(response.indexOf("}") + 1);
                }
                for(String responseString : responseStrings){
                    JSONObject json = new JSONObject(responseString);
                    ArrayList<String> objectValues = new ArrayList<>();
                    for(String arg : strings[1]){
                        objectValues.add(json.getString(arg));
                    }
                    processedData.add(objectValues);
                }
                return processedData;
            } catch(Exception e) {
//                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<List<String>> objectValues) {
            super.onPostExecute(objectValues);
            updateValues(objectValues);
        }
    }

    class AsyncTimedFetch extends AsyncTask<String[], Void, List<List<String>>>{

        @Override
        protected List<List<String>> doInBackground(String[]... strings) {
            try{
                //Log.i("msg", "in async task");
                ArrayList<String> responseStrings = new ArrayList<>();
                ArrayList<List<String>> processedData = new ArrayList<>();
                URL url = new URL(strings[0][0] + "?" + compilePath(strings[1], strings[2]));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = rd.readLine();
                while (response.contains("{")){
                    responseStrings.add(response.substring(response.indexOf("{"), response.indexOf("}") + 1));
                    response = response.substring(response.indexOf("}") + 1);
                }
                for(String responseString : responseStrings){
                    JSONObject json = new JSONObject(responseString);
                    ArrayList<String> objectValues = new ArrayList<>();
                    for(String arg : strings[1]){
                        objectValues.add(json.getString(arg));
                    }
                    processedData.add(objectValues);
                }
                return processedData;
            } catch(Exception e) {
//                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<List<String>> objectValues) {
            super.onPostExecute(objectValues);
            updateValues(objectValues);
        }
    }

    private String compilePath(String[] args, String[] params){
        String ret = "";
        ret += args[0] + "=" + params[0];
        for(int i = 1; i < args.length; i++){
            ret += "&" + args[i] + "=" + params[i];
        }
        return ret;
    }
}
