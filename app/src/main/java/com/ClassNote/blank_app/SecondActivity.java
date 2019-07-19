package com.ClassNote.blank_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Change the text in TextView to greet logged on user
        if(getIntent().hasExtra("com.ClassNote.login.name")){
            /* Extras is a bundle saved into the intent and can be used to pass data through
               different activities */
            TextView greetingsText = findViewById(R.id.greetingsTxt);
            String welcomeText = "Welcome: " + getIntent().getExtras().getString(MainActivity.LOGIN_NAME) + "!";
            greetingsText.setText(welcomeText);
        }


    }
}
