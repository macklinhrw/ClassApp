package com.ClassNote.blank_app;

import androidx.appcompat.app.AppCompatActivity;

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
        descriptionEditText.setText(activeUser.getDescription());
        emailEditText.setText(activeUser.getEmail());
        birthdateEditText.setText(activeUser.getBirthDate());
        onboardTextView.setText(activeUser.getOnboard());
        userIDTextView.setText(activeUser.getId());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "This hasn't been implemented yet!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
