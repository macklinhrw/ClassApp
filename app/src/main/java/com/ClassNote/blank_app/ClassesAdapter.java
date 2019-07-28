package com.ClassNote.blank_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.MyViewHolder> {

    public ClassesAdapter(){
        // TODO : Dataset here?
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
    }

    @Override
    public int getItemCount() {
        // TODO : Find max amount able to be held by HomeActivity
        return 10;
    }
}
