package com.ClassNote.blank_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    // TODO: Texting layout and messages setup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Button exitBtn = findViewById(R.id.exitBtn);

        // Change the text in TextView to greet logged on user
        if(getIntent().hasExtra(MainActivity.ACTIVE_USER)){
            /* Extras is a bundle saved into the intent and can be used to pass data through
               different activities */
            TextView greetingsText = findViewById(R.id.greetingsTxt);
            User activeUser = getIntent().getExtras().getParcelable(MainActivity.ACTIVE_USER);
            String welcomeText = "Welcome: " + activeUser.getName() + "!";
            greetingsText.setText(welcomeText);
        }

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
