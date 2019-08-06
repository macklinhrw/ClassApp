package com.ClassNote.blank_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.adapters.ThreadAdapter;
import com.ClassNote.blank_app.utilities.Path;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.User;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        final RecyclerView threadsRecyclerView = findViewById(R.id.threadsRecyclerView);
        final Button homeBtn = findViewById(R.id.classhomebutton);
        final Button docBtn = findViewById(R.id.classdocsbutton);
        final Button calBtn = findViewById(R.id.classcalendarbutton);

        User activeUser = getIntent().getExtras().getParcelable(Path.ACTIVE_USER.str);
        int pos = getIntent().getExtras().getInt(Path.ACTIVE_CLASS.str);

        threadsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        threadsRecyclerView.setLayoutManager(layoutManager);
        List<SchoolClass> sc = activeUser.fetchClasses();
        SchoolClass c = null;
        if(sc != null){
            c = sc.get(pos);
        }
        if( c != null && c.getThreads() == null){
            mAdapter = new ThreadAdapter(new ArrayList<>(), activeUser);
            threadsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = new ThreadAdapter(c.getThreads(), activeUser);
            threadsRecyclerView.setAdapter(mAdapter);
        }

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ClassHomeActivity.class);
                startIntent.putExtra(Path.ACTIVE_USER.str, activeUser);
                startIntent.putExtra(Path.ACTIVE_CLASS.str, pos);
                startActivity(startIntent);
                finish();
            }
        });

        // TODO make ClassDocsActivity connect to this
//        docBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent startIntent = new Intent(getApplicationContext(), ClassDocsActivity.class);
//                startActivity(startIntent);
//                finish();
//            }
//        });

        // TODO make ClassCalendarActivity connect to this
//        calBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent startIntent = new Intent(getApplicationContext(), ClassCalendarActivity.class);
//                startActivity(startIntent);
//                finish();
//            }
//        });
    }
}
