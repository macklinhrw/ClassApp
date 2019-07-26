package com.ClassNote.blank_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // TODO : Refactor + change id to logoutBtn
        final Button exitBtn = findViewById(R.id.exitBtn);
        final Button profileBtn = findViewById(R.id.profileBtn);
        final TextView greetingsText = findViewById(R.id.greetingsTxt);

        // Change the text in TextView to greet logged on user
        /* Extras is a bundle saved into the intent and can be used to pass data through
           different activities */
        User activeUser = getIntent().getExtras().getParcelable(LoginActivity.ACTIVE_USER);
        String welcomeText = activeUser.getName() + "!";
        greetingsText.setText(welcomeText);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startIntent.putExtra(LoginActivity.ACTIVE_USER, activeUser);
                startActivity(startIntent);
            }
        });


    }
}
