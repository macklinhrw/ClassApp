package com.ClassNote.blank_app.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.data.ConnectMySQL2;
import com.ClassNote.blank_app.data.Path;
import com.ClassNote.blank_app.data.ThreadClass;
import com.ClassNote.blank_app.data.User;
import com.ClassNote.blank_app.ui.MessagesActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.MyViewHolder> {

    private List<ThreadClass> threads;
    private User activeUser;

    public ThreadAdapter(List<ThreadClass> threads, User activeUser){
        this.threads = threads;
        this.activeUser = activeUser;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpater_thread, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TextView nameTextView = holder.view.findViewById(R.id.nameTextView);
        final TextView previewTextView = holder.view.findViewById(R.id.previewTextView);;
        final Button detailsBtn = holder.view.findViewById(R.id.detailsBtn);

        ThreadClass t = threads.get(position);

        nameTextView.setText(t.getName());

        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(holder.view.getContext(), String.valueOf(t.getId()), Toast.LENGTH_SHORT).show();
                Log.i("toast", t.getId());

                Intent startIntent = new Intent(holder.view.getContext(), MessagesActivity.class);
                startIntent.putExtra(Path.ACTIVE_THREAD.str, t);
                startIntent.putExtra(Path.ACTIVE_USER.str, activeUser);
                holder.view.getContext().startActivity(startIntent);
                //((Activity)holder.view.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View view;
        public MyViewHolder(View v){
            super(v);
            view = v;
        }
    }
}
