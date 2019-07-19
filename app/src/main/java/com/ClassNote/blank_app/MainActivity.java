package com.ClassNote.blank_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String LOGIN_NAME = "com.ClassNote.login.name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginBtn  = findViewById(R.id.loginBtn);
        final Button githubBtn = findViewById(R.id.githubBtn);
        final EditText loginName = findViewById(R.id.loginNameEditText);

        // Checks Login button clicks for user login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                    //loginName.
                    startIntent.putExtra(LOGIN_NAME, loginName.getText().toString());
                    startActivity(startIntent);
            }
        });

        githubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String githubAddress = "https://github.com/macklinhrw/ClassApp";
                Uri address = Uri.parse(githubAddress);

                //Launches devices webbrowser on link address
                Intent webRedirect = new Intent(Intent.ACTION_VIEW, address);
                if(webRedirect.resolveActivity(getPackageManager()) != null){
                    startActivity(webRedirect);
                }
            }
        });

    }
}
