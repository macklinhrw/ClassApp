package com.ClassNote.blank_app.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.StrictMode;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.ClassNote.blank_app.data.ConnectUser;
import com.ClassNote.blank_app.data.Path;
import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.data.User;

public class LoginActivity extends AppCompatActivity {

    // TODO : ViewModels, Observe(), Fragments

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

        LoginViewModel loginViewModel = new LoginViewModel();
        User activeUser = new User();

        loginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String loginResult) {
                if (loginResult == null) {
                    return;
                }
                loading.setVisibility(View.GONE);
                if (loginResult.equals("failed")) {
                    Toast.makeText(getApplicationContext(), "Wrong user credentials!", Toast.LENGTH_SHORT).show();
                }
                if (!loginResult.equals("failed")) {
                    //Log.i("userlogin", loginResult);
                    activeUser.updateUser(loginResult);
                    Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startIntent.putExtra(Path.ACTIVE_USER.str, activeUser);
                    startActivity(startIntent);
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        // Checks Login button clicks for user login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.setVisibility(View.VISIBLE);

                ConnectUser c = new ConnectUser();
                c.login(loginNameEditText.getText().toString(), passwordEditText.getText().toString(), loginViewModel);
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
