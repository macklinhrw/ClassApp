package com.ClassNote.blank_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.StrictMode;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ClassNote.blank_app.Enums.Path;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginBtn  = findViewById(R.id.loginBtn);
        final Button githubBtn = findViewById(R.id.githubBtn);
        final EditText loginNameEditText = findViewById(R.id.loginUserEditTxt);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final TextView registerTextView = findViewById(R.id.registerTextView);
        final ProgressBar loading = findViewById(R.id.loginLoading);

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

                // TODO : Fix loading bar, change to an observe()?
                loading.setVisibility(View.VISIBLE);

                User activeUser = new User(loginNameEditText.getText().toString(), passwordEditText.getText().toString());

                if(activeUser.getCredentials() != User.FAILED_CREDENTIALS && activeUser.getCredentials() != User.UNCONFIRMED_CREDENTIALS){
                    Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startIntent.putExtra(Path.ACTIVE_USER.str, activeUser);
                    startActivity(startIntent);
                    loading.setVisibility(View.GONE);
                    finish();
                } else {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Wrong user credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });

        githubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri address = Uri.parse(Path.GITHUB_ADDRESS.str);

                // Launches devices webbrowser on link address
                Intent webRedirect = new Intent(Intent.ACTION_VIEW, address);
                if(webRedirect.resolveActivity(getPackageManager()) != null){
                    startActivity(webRedirect);
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), NewUserActivity.class);
                // Passes already filled out username and starts NewUserActivity
                String user = loginNameEditText.getText().toString();
                if(!user.equals("")){
                    startIntent.putExtra(Path.USERNAME.str, user);
                }
                startActivity(startIntent);
            }
        });

    }
}
