package com.ClassNote.blank_app.adapters;

import android.graphics.Color;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.data.MessageClass;
import com.ClassNote.blank_app.data.SchoolClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    List<MessageClass> messages;

    private static final long MESSAGE_COMBINE_PERIOD = 60000;

    public MessagesAdapter(List<MessageClass> messages){
        this.messages = messages;
    }

    public void setData(List<MessageClass> newData) {
        this.messages = newData;
        notifyDataSetChanged();
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
        final TextView timeTextView = holder.view.findViewById(R.id.timeTextView);
        final ImageView profileImageView = holder.view.findViewById(R.id.profileImageView);
        final ConstraintLayout constraintMessagesLayout = holder.view.findViewById(R.id.constrainMessagesLayout);

        MessageClass ms = messages.get(position);

        senderNameTextView.setText(ms.getAuthor());
        messageTextView.setText(ms.getText());
        messageTextView.setMaxWidth(800);


        if(ms.getAuthor().equals("elig")){
            profileImageView.setImageResource(R.drawable.circle2);
        } else {
            profileImageView.setImageResource(R.drawable.circle);
        }

        // formats
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat longFormat = new SimpleDateFormat("M/d/yy");
        SimpleDateFormat weekFormat = new SimpleDateFormat("'Last' EEEE 'at' h:m a");
        SimpleDateFormat shortFormat = new SimpleDateFormat("EEEE 'at' h:mm a");
        SimpleDateFormat yesterdayFormat = new SimpleDateFormat("'Yesterday at' h:mm a");
        SimpleDateFormat todayFormat = new SimpleDateFormat("'Today at' h:mm a");

        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        // Formatting for the date
        String date;

        Calendar now = Calendar.getInstance();
        Calendar nowBlank = Calendar.getInstance();
        Calendar then = Calendar.getInstance();

        Date thenDate = null;
        try {
            thenDate = dateFormat.parse(ms.getLocal_datetime());

            nowBlank.set(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DATE),0,0,0);
            then.setTimeInMillis(thenDate.getTime());

//            Log.i("dmes", "" + (nowBlank.getTimeInMillis() - thenDate.getTime()));
//            Log.i("dmes", "Now: " + nowBlank.getTimeInMillis());
//            Log.i("dmes", "Then: " + thenDate.getTime());
//            Log.i("dmes", "Unformatted: " + ms.getLocalDateTime());
//            Log.i("dmes", "Mili of date: " + thenDate.getTime());

            long timeSince = nowBlank.getTimeInMillis() - thenDate.getTime();
            if(timeSince >= 691200000){
                date = longFormat.format(thenDate);
            } else if(timeSince >= 259200000){
                if(now.get(Calendar.DAY_OF_WEEK) - then.get(Calendar.DAY_OF_WEEK) < 0){
                    date = weekFormat.format(thenDate);
                } else {
                    date = shortFormat.format(thenDate);
                }
            } else if(timeSince >= 86400000){
                date = yesterdayFormat.format(thenDate);
            } else {
                if(now.get(Calendar.DAY_OF_WEEK) - then.get(Calendar.DAY_OF_WEEK) != 0){
                    date = yesterdayFormat.format(thenDate);
                } else {
                    date = todayFormat.format(thenDate);
                }
            }
            timeTextView.setText(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO : combine this code and make it better
        // Combining messages
        if(position > 0 && ms.getAuthor().equals(messages.get(position - 1).getAuthor())) {
            MessageClass msLast = messages.get(position - 1);

            try {
                Date lastTime = dateFormat.parse(msLast.getLocal_datetime());
                if(thenDate != null && thenDate.getTime() - lastTime.getTime() <= MESSAGE_COMBINE_PERIOD){
                    timeTextView.setVisibility(View.GONE);
                    senderNameTextView.setVisibility(View.GONE);
                    profileImageView.setVisibility(View.GONE);
                    constraintMessagesLayout.setPadding(32,0,16,0);
                } else {
                    timeTextView.setVisibility(View.VISIBLE);
                    senderNameTextView.setVisibility(View.VISIBLE);
                    profileImageView.setVisibility(View.VISIBLE);
                    constraintMessagesLayout.setPadding(32,16,16,16);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            timeTextView.setVisibility(View.VISIBLE);
            senderNameTextView.setVisibility(View.VISIBLE);
            profileImageView.setVisibility(View.VISIBLE);
            constraintMessagesLayout.setPadding(32,16,16,16);
        }

        if(position != messages.size() - 1 && messages.get(position + 1).getAuthor().equals(messages.get(position).getAuthor())){
            MessageClass msNext = messages.get(position + 1);
            try {
                Date nextTime = dateFormat.parse(msNext.getLocal_datetime());
                if(thenDate != null && nextTime.getTime() - thenDate.getTime() <= MESSAGE_COMBINE_PERIOD){
                    constraintMessagesLayout.setPadding(32, constraintMessagesLayout.getPaddingTop(),16,0);
                } else {
                    constraintMessagesLayout.setPadding(32, constraintMessagesLayout.getPaddingTop(),16,16);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            constraintMessagesLayout.setPadding(32, constraintMessagesLayout.getPaddingTop(),16,16);
        }

    }

    @Override
    public int getItemCount() {
        if(messages == null){
            return 0;
        }
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
