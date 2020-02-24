package com.example.eventinapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eventinapp.R;
import com.example.eventinapp.activity.DescOfferActivity;
import com.example.eventinapp.model.Company;

import java.util.ArrayList;

public class CardViewCompanyAdapter extends RecyclerView.Adapter<CardViewCompanyAdapter.CardViewViewHolder>{
    private ArrayList<Company> listCompany;

    public CardViewCompanyAdapter(ArrayList<Company> list) {
        this.listCompany = list;
    }


    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company,parent,false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position){
        final Company company = listCompany.get(position);

        Glide.with(holder.itemView.getContext())
                .load(company.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPhoto);
        holder.tvName.setText(company.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DescOfferActivity.class);
                i.putExtra("title",company.getName()); //sending title of the cardview
                i.putExtra("desc",company.getDescribe());
                i.putExtra("image",company.getPhoto());
                v.getContext().startActivity(i); // starting next Activity from view v.
            }
        });

    }


    @Override
    public int getItemCount() {
        return listCompany.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvName;
        TextView descCompany;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);


        }
    }


}
