package com.ClassNote.blank_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.utilities.Path;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.User;

import android.widget.*;
import java.util.List;

public class ClassHomeActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classhome);

        final TextView titletext = findViewById(R.id.classhome_title);
        final TextView teacherperiod = findViewById(R.id.teacherperiod);
        final TextView description = findViewById(R.id.classdescription);
        final Button threadsBtn = findViewById(R.id.classthreadsbutton2);
        final Button docBtn = findViewById(R.id.classdocsbutton2);
        final Button calBtn = findViewById(R.id.classcalendarbutton2);

        User activeUser = getIntent().getExtras().getParcelable(Path.ACTIVE_USER.str);
        int pos = getIntent().getExtras().getInt(Path.ACTIVE_CLASS.str);

        layoutManager = new LinearLayoutManager(this);
        List<SchoolClass> sc = activeUser.fetchClasses();
        SchoolClass c = null;
        if(sc != null){
            c = sc.get(pos);
        }
        if(c != null){
            titletext.setText(c.getName());
            String tp = c.getTeacher()+" - "+c.getPeriod();
            teacherperiod.setText(tp);
            description.setText(c.getDescription());
        }

        threadsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ClassActivity.class);
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

