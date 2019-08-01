package com.ClassNote.blank_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.adapters.MessagesAdapter;
import com.ClassNote.blank_app.adapters.ThreadAdapter;
import com.ClassNote.blank_app.data.ConnectMySQL2;
import com.ClassNote.blank_app.data.MessageClass;
import com.ClassNote.blank_app.data.Path;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.ThreadClass;
import com.ClassNote.blank_app.data.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        final RecyclerView messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        final MultiAutoCompleteTextView sendMessageTextView = findViewById(R.id.sendMessageTextView);
        final Button sendBtn = findViewById(R.id.sendBtn);
        ConnectMySQL2 c = new ConnectMySQL2();

        ThreadClass tc = getIntent().getExtras().getParcelable(Path.ACTIVE_THREAD.str);
        User activeUser = getIntent().getExtras().getParcelable(Path.ACTIVE_USER.str);

        ArrayList<MessageClass> messages = tc.fetchMessages();

        messagesRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MessagesAdapter(messages);
        messagesRecyclerView.setAdapter(mAdapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getDefault());
                Date d = cal.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = dateFormat.format(d);

                c.sendMessage(activeUser.getId(), tc.getId(), activeUser.getName(),sendMessageTextView.getText().toString());
                messages.add(new MessageClass("", activeUser.getId(),tc.getId(),date,sendMessageTextView.getText().toString(), activeUser.getName()));
                //mAdapter.notifyItemInserted(messages.size() - 1);
                mAdapter.notifyDataSetChanged();
                messagesRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                sendMessageTextView.setText("");
            }
        });
    }
}
