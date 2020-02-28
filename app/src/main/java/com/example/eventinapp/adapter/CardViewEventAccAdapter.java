package com.example.eventinapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eventinapp.R;
import com.example.eventinapp.activity.AcceptUploadActivity;
import com.example.eventinapp.model.Event;

import java.util.ArrayList;

public class CardViewEventAccAdapter extends RecyclerView.Adapter<CardViewEventAccAdapter.CardViewViewHolder> {
    private ArrayList<Event> listEvent;

    public CardViewEventAccAdapter(ArrayList<Event> list) {
        this.listEvent = list;
    }


    @NonNull
    @Override
    public CardViewEventAccAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        return new CardViewEventAccAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewEventAccAdapter.CardViewViewHolder holder, int position){
        final Event event = listEvent.get(position);


        holder.nameEvent.setText(event.getEventName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AcceptUploadActivity.class);
                v.getContext().startActivity(i); // starting next Activity from view v.
            }
        });

    }


    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView nameEvent;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            nameEvent = itemView.findViewById(R.id.tv_name_event);


        }
    }


}