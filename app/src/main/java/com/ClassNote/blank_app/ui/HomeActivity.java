package com.ClassNote.blank_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ClassNote.blank_app.adapters.ClassesAdapter;
import com.ClassNote.blank_app.data.Path;
import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.User;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button logoutBtn = findViewById(R.id.logoutBtn);
        final Button profileBtn = findViewById(R.id.profileBtn);
        final TextView greetingsText = findViewById(R.id.greetingsTxt);
        final RecyclerView classesRecyclerView = findViewById(R.id.classesRecyclerView);

        // Change the text in TextView to greet logged on user
        /* Extras is a bundle saved into the intent and can be used to pass data through
           different activities */
        User activeUser = getIntent().getExtras().getParcelable(Path.ACTIVE_USER.str);
        String welcomeText = activeUser.getName() + "!";
        greetingsText.setText(welcomeText);

        // Setup adapter for classes recyclerview
        classesRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        classesRecyclerView.setLayoutManager(layoutManager);

        // Updates adapter's dataset based on user's classes HERE
        List<SchoolClass> classes = activeUser.fetchClasses();
        mAdapter = new ClassesAdapter(classes, activeUser);

        classesRecyclerView.setAdapter(mAdapter);

        // Check for new user
        if(activeUser.getDescription().equals("null") || activeUser.getBirthDate().equals("null")){
            Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            startIntent.putExtra(Path.ACTIVE_USER.str, activeUser);
            startActivity(startIntent);
            finish();
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeUser.logout();
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startIntent);
                finish();
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startIntent.putExtra(Path.ACTIVE_USER.str, activeUser);
                startActivity(startIntent);
                finish();
            }
        });

    }
}
