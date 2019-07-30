package com.ClassNote.blank_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.data.MessageClass;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    List<MessageClass> messages;

    public MessagesAdapter(List<MessageClass> messages){
        this.messages = messages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_messages, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TextView senderNameTextView = holder.view.findViewById(R.id.senderNameTextView);
        final TextView messageTextView = holder.view.findViewById(R.id.messageTextView);

        MessageClass ms = messages.get(position);

        senderNameTextView.setText(ms.getAuthor());
        messageTextView.setText(ms.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View view;
        public MyViewHolder(View v){
            super(v);
            view = v;
        }
    }
}
