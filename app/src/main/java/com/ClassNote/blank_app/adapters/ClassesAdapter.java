package com.ClassNote.blank_app.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ClassNote.blank_app.R;
import com.ClassNote.blank_app.utilities.Path;
import com.ClassNote.blank_app.data.SchoolClass;
import com.ClassNote.blank_app.data.User;
import com.ClassNote.blank_app.ui.ClassHomeActivity;

import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.MyViewHolder> {

    private List<SchoolClass> classes;
    private User activeUser;

    public ClassesAdapter(List<SchoolClass> classes, User activeUser){
        this.classes = classes;
        this.activeUser = activeUser;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View view;
        public MyViewHolder(View v){
            super(v);
            view = v;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_class, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // TODO : set text corresponding to dataset here
        final TextView classNameTextView = holder.view.findViewById(R.id.classNameTextView);
        final TextView teacherTextView = holder.view.findViewById(R.id.teacherTextView);
        final TextView periodTextView = holder.view.findViewById(R.id.periodTextView);
        final Button detailsBtn = holder.view.findViewById(R.id.detailsBtn);

        SchoolClass curClass = classes.get(position);

        classNameTextView.setText(curClass.getName());
        teacherTextView.setText(curClass.getTeacher() + " -");
        periodTextView.setText(String.valueOf(curClass.getPeriod()));

        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(holder.view.getContext(), curClass.getDescription(), Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(holder.view.getContext(), ClassHomeActivity.class);
                startIntent.putExtra(Path.ACTIVE_USER.str, activeUser);
                startIntent.putExtra(Path.ACTIVE_CLASS.str, position);
                holder.view.getContext().startActivity(startIntent);
                //((Activity)holder.view.getContext()).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        // TODO : Find max amount able to be held by HomeActivity
//        if(classes == null){
//            return 0;
//        }
        return classes.size();
    }
}
