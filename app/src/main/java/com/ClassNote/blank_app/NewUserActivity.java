package com.ClassNote.blank_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ClassNote.blank_app.ConnectMySQL;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final EditText registerUserEditTxt = findViewById(R.id.registerUserEditTxt);
        final EditText registerPasswordEditText = findViewById(R.id.registerPasswordEditText);
        final EditText registerEmailEditText = findViewById(R.id.registerEmailEditText);
//        final EditText registerNameEditText = findViewById(R.id.registerNameEditText);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectMySQL c = new ConnectMySQL();
                String username = registerUserEditTxt.getText().toString();
                String password = registerPasswordEditText.getText().toString();
                String email = registerEmailEditText.getText().toString();
//                String name = registerNameEditText.getText().toString();
                String name = "Java Test";
                //TODO add name field
                String newUser = c.newUser(username, password, name, email);
                if(newUser.equals("success")) {
                    Toast.makeText(getApplicationContext(), "New user created!", Toast.LENGTH_LONG).show();
                    //TODO automatically logs in at this point
                } else if(newUser.equals("username taken")) {
                    Toast.makeText(getApplicationContext(), "Username was already taken. Try another.", Toast.LENGTH_LONG).show();
                } else if(newUser.equals("unknown failure")) {
                    Toast.makeText(getApplicationContext(), "An unknown failure occurred in creating the new user. Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
