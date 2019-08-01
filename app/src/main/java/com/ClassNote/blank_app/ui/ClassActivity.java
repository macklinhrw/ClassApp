package com.ClassNote.blank_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.adapters.ThreadAdapter;
import com.ClassNote.blank_app.data.Path;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.ThreadClass;
import com.ClassNote.blank_app.data.User;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        final RecyclerView threadsRecyclerView = findViewById(R.id.threadsRecyclerView);

        User activeUser = getIntent().getExtras().getParcelable(Path.ACTIVE_USER.str);
        int pos = getIntent().getExtras().getInt(Path.ACTIVE_CLASS.str);

        threadsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        threadsRecyclerView.setLayoutManager(layoutManager);
        SchoolClass c = activeUser.fetchClasses().get(pos);
        if( c.getThreads() == null){
            mAdapter = new ThreadAdapter(new ArrayList<ThreadClass>(), activeUser);
            threadsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = new ThreadAdapter(c.getThreads(), activeUser);
            threadsRecyclerView.setAdapter(mAdapter);
        }
    }
}
