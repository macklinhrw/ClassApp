package com.ClassNote.blank_app.data;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import com.ClassNote.blank_app.ui.LoginViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectUser {

    private LoginViewModel viewModel;

    public void login(String username, String password, LoginViewModel v){
        LoginTask l = new LoginTask();
        viewModel = v;
        l.execute(username, password);
    }

    public void processFinish(String result) {
        viewModel.setLoginResult(result);
    }

    private class LoginTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://classnoteutil.000webhostapp.com/users_connect.php?username="+strings[0]+"&password="+strings[1]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                return rd.readLine();
            } catch(Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            processFinish(s);
        }
    }
}
