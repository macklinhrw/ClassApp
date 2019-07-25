package com.ClassNote.blank_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String ACTIVE_USER = "com.ClassNote.user.active";
    public static final String GITHUB_ADDRESS = "https://github.com/macklinhrw/ClassApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginBtn  = findViewById(R.id.loginBtn);
        final Button githubBtn = findViewById(R.id.githubBtn);
        final EditText loginNameEditText = findViewById(R.id.loginUserEditTxt);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Checks Login button clicks for user login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User activeUser = new User(loginNameEditText.getText().toString(), passwordEditText.getText().toString());

                if(activeUser.getCredentials() != User.FAILED_CREDENTIALS && activeUser.getCredentials() != User.UNCONFIRMED_CREDENTIALS){

                    Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                    startIntent.putExtra(ACTIVE_USER, activeUser);
                    startActivity(startIntent);
                }
            }
        });

        githubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri address = Uri.parse(GITHUB_ADDRESS);

                // Launches devices webbrowser on link address
                Intent webRedirect = new Intent(Intent.ACTION_VIEW, address);
                if(webRedirect.resolveActivity(getPackageManager()) != null){
                    startActivity(webRedirect);
                }
            }
        });

    }
}
