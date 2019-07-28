package com.ClassNote.blank_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.MyViewHolder> {

    private List<SchoolClass> classes;

    public ClassesAdapter(List<SchoolClass> classes){
        this.classes = classes;
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
                Toast.makeText(holder.view.getContext(), curClass.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        // TODO : Find max amount able to be held by HomeActivity
        return classes.size();
    }
}
