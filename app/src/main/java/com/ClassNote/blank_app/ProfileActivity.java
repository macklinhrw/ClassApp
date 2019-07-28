package com.ClassNote.blank_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final EditText nicknameEditText = findViewById(R.id.profileNicknameEditText);
        final EditText fullnameEditText = findViewById(R.id.profileFullnameEditText);
        final EditText descriptionEditText = findViewById(R.id.profileDescriptionEditText);
        final EditText emailEditText = findViewById(R.id.profileEmailEditText);
        final EditText birthdateEditText = findViewById(R.id.profileBirthdateEditText);

        final TextView onboardTextView = findViewById(R.id.profileOnboardTextView);
        final TextView userIDTextView = findViewById(R.id.profileUserIDTextView);

        final Button backBtn = findViewById(R.id.profileBackBtn);
        final Button saveBtn = findViewById(R.id.profileSaveBtn);

        User activeUser = getIntent().getExtras().getParcelable(LoginActivity.ACTIVE_USER);

        nicknameEditText.setText(activeUser.getUsername());
        fullnameEditText.setText(activeUser.getName());
        emailEditText.setText(activeUser.getEmail());
        onboardTextView.setText(activeUser.getOnboard());
        userIDTextView.setText(activeUser.getId());

        if(activeUser.getDescription().equals("null") & activeUser.getBirthDate().equals("null")) {
            Toast.makeText(getApplicationContext(), "Update your user info!", Toast.LENGTH_LONG).show();
            descriptionEditText.setText("");
            birthdateEditText.setText("");
        } else if (activeUser.getDescription().equals("null")) {
            Toast.makeText(getApplicationContext(), "Update your description!", Toast.LENGTH_LONG).show();
            descriptionEditText.setText("");
            birthdateEditText.setText(activeUser.getBirthDate());
        } else if(activeUser.getBirthDate().equals("null")){
            Toast.makeText(getApplicationContext(), "Update your birthdate!", Toast.LENGTH_LONG).show();
            birthdateEditText.setText("");
            descriptionEditText.setText(activeUser.getDescription());
        } else {
            birthdateEditText.setText(activeUser.getBirthDate());
            descriptionEditText.setText(activeUser.getDescription());
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Find a better way to pass the information back without closing the homeactivity?
                Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startIntent.putExtra(LoginActivity.ACTIVE_USER, activeUser);
                startActivity(startIntent);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FIXME if you go back to home and back to profile
                // the changes you just made will not appear
                // because I don't know how to save / load the new attribs globally
                // but it does change it in the DB
                // so if you log out and back in it shows the new attribs correctly
                ConnectMySQL c = new ConnectMySQL();
                String id = activeUser.getId();
                String username = nicknameEditText.getText().toString();
                if(username == activeUser.getUsername()) {
                    username = null;
                } else {
                    activeUser.setUsername(username);
                }
                String email = emailEditText.getText().toString();
                if(email == activeUser.getEmail()) {
                    email = null;
                } else {
                    activeUser.setEmail(email);
                }
                String name = fullnameEditText.getText().toString();
                if(name == activeUser.getName()) {
                    name = null;
                } else {
                    activeUser.setName(name);
                }
                String birth = birthdateEditText.getText().toString();
                if(birth == activeUser.getBirthDate()) {
                    birth = null;
                } else {
                    activeUser.setBirthDate(birth);
                }
                String description = descriptionEditText.getText().toString();
                if(description == activeUser.getDescription()) {
                    description = null;
                } else {
                    activeUser.setDescription(description);
                }
                String response = c.updateUser(id, email, name, birth, description, username);
                if(!response.contains("error")) {
                    Toast.makeText(getApplicationContext(), "User information updated successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "There was one or more errors when updating info.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
