package com.ClassNote.blank_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.adapters.MessagesAdapter;
import com.ClassNote.blank_app.adapters.ThreadAdapter;
import com.ClassNote.blank_app.data.ConnectMySQL2;
import com.ClassNote.blank_app.data.FormattedDate;
import com.ClassNote.blank_app.data.MessageClass;
import com.ClassNote.blank_app.data.Path;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.ThreadClass;
import com.ClassNote.blank_app.data.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessagesActivity extends AppCompatActivity {

    private MessagesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //Log.i("msg", "here1");

        final RecyclerView messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        final MultiAutoCompleteTextView sendMessageTextView = findViewById(R.id.sendMessageTextView);
        final Button sendBtn = findViewById(R.id.sendBtn);
        final ProgressBar loading = findViewById(R.id.loadingProgressBar);
        ConnectMySQL2 c = new ConnectMySQL2();
        MessagesViewModel model = new MessagesViewModel();

        //Log.i("msg", "here2");

        ThreadClass tc = getIntent().getExtras().getParcelable(Path.ACTIVE_THREAD.str);
        User activeUser = getIntent().getExtras().getParcelable(Path.ACTIVE_USER.str);

        model.fetchMessages(tc.getId());
        List<MessageClass> localMessages = model.getMessages().getValue();

        //Log.i("msg", "here3");

        messagesRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(layoutManager);
        model.updateMessages(localMessages);
        mAdapter = new MessagesAdapter(localMessages);
        messagesRecyclerView.setAdapter(mAdapter);

        //Log.i("msg", "here4");
        loading.setVisibility(View.VISIBLE);

        model.getMessages().observe(this, new Observer<List<MessageClass>>() {
            @Override
            public void onChanged(List<MessageClass> messageClasses) {

                if(messageClasses == null){
                    return;
                }
                if(loading.getVisibility() == View.VISIBLE){
                    loading.setVisibility(View.GONE);
                }
                mAdapter.setData(messageClasses);
                messagesRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                if(!model.isOpen())
                    model.connectNewMessages(tc.getId(), messageClasses.get(messageClasses.size() - 1).getUtc_datetime());

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormattedDate d = new FormattedDate();
                String date = d.createUTC();

                //Log.i("msg", date);

                c.sendMessage(activeUser.getId(), tc.getId(), activeUser.getUsername(), sendMessageTextView.getText().toString());
                //mAdapter.notifyItemInserted(messages.size() - 1);
                //mAdapter.notifyDataSetChanged();
                messagesRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                sendMessageTextView.setText("");
            }
        });
    }
}
