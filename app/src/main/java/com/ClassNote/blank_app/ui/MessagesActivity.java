package com.ClassNote.blank_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.adapters.MessagesAdapter;
import com.ClassNote.blank_app.adapters.ThreadAdapter;
import com.ClassNote.blank_app.data.Path;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.ThreadClass;
import com.ClassNote.blank_app.data.User;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        final RecyclerView threadsRecyclerView = findViewById(R.id.messagesRecyclerView);

        ThreadClass tc = getIntent().getExtras().getParcelable(Path.ACTIVE_THREAD.str);

        threadsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        threadsRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MessagesAdapter(tc.fetchMessages());
        threadsRecyclerView.setAdapter(mAdapter);
    }
}
