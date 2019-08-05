package com.ClassNote.blank_app.data;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import com.ClassNote.blank_app.data.ConnectMySQL2;

import java.io.File;

public class UploadImage {

    public void UploadImage(String filePath, String user_id) {
        try{
            URL url = new URL("http://classnoteutil.000webhostapp.com/kid.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String MY_ACCESS_KEY_ID = rd.readLine();
            URL url2 = new URL("http://classnoteutil.000webhostapp.com/sk.php");
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            BufferedReader rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
            String MY_SECRET_KEY = rd2.readLine();
            AmazonS3Client s3Client =   new AmazonS3Client(new BasicAWSCredentials(MY_ACCESS_KEY_ID, MY_SECRET_KEY));
            final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyz";
            final int N = alphabet.length();
            Random r = new Random();
            String picture_filename = "";
            for (int i = 0; i < 30; i++) {
                picture_filename += alphabet.charAt(r.nextInt(N));
            }
            picture_filename += ".png";
            PutObjectRequest por =    new PutObjectRequest("classnoteusericons", picture_filename, filePath);
            s3Client.putObject(por);
            ConnectMySQL2 c = new ConnectMySQL2();
            c.updateUserIcon(user_id, picture_filename);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}