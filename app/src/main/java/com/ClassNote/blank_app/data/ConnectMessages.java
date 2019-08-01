package com.ClassNote.blank_app.data;

import android.os.AsyncTask;

import com.ClassNote.blank_app.ui.MessagesViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

public class ConnectMessages {

    private MessagesViewModel model;
    private NewMessagesTask mt;
    private String thread;
    private String time;
    private boolean finishTask;
    private long lasttime;

    public void updateNewMessages(List<MessageClass> result) {
        if(result != null){
            model.addMessages(result);
        }
        if(!finishTask){
            // TODO : wait certain time?
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    NewMessagesTask nmt = new NewMessagesTask();
                    if(result != null){
                        String newTime = result.get(result.size() - 1).getUtc_datetime();
                        time = newTime;
                        nmt.execute(thread, newTime);
                    } else {
                        nmt.execute(thread, time);
                    }
                }
            }, 1000);
        }
    }

    public void onLoadMessages(List<MessageClass> messages){
        model.updateMessages(messages);
    }

    public ConnectMessages(MessagesViewModel model){
        this.model = model;
    }

    public void startNewMessagesTask(String thread_id, String timestamp, boolean finishTask){
        this.finishTask = finishTask;
        thread = thread_id;
        time = timestamp;
        mt = new NewMessagesTask();
        mt.execute(thread_id, timestamp);
    }

    public void setFinishTask(boolean finishTask){
        this.finishTask = finishTask;
    }

    public void loadMessages(String thread_id){
        FetchMessagesTask fmt = new FetchMessagesTask();
        fmt.execute(thread_id);
    }

    class NewMessagesTask extends AsyncTask<String, Void, List<MessageClass>>{

        @Override
        protected List<MessageClass> doInBackground(String... strings) {
            try{
                //Log.i("msg", "in async task");
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
//                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<MessageClass> messageClasses) {
            super.onPostExecute(messageClasses);
            updateNewMessages(messageClasses);
        }
    }

    class FetchMessagesTask extends AsyncTask<String, Void, List<MessageClass>>{

        @Override
        protected List<MessageClass> doInBackground(String... strings) {
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
        protected void onPostExecute(List<MessageClass> messageClasses) {
            super.onPostExecute(messageClasses);
            onLoadMessages(messageClasses);
        }
    }
}