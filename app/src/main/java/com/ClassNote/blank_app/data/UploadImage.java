package com.ClassNote.blank_app.data;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import java.util.Random;
import com.ClassNote.blank_app.data.ConnectMySQL2;

import java.io.File;

public class UploadImage {

    public void UploadImage(String filePath, String user_id) {
        String MY_ACCESS_KEY_ID = "AKIA2I5IBE5MYN6I2I6D";
        String MY_SECRET_KEY = "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";
        AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials(MY_ACCESS_KEY_ID, MY_SECRET_KEY));
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
    }

}