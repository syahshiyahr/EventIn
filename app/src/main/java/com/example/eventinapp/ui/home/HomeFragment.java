package com.example.eventinapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventinapp.activity.LoginActivity;
import com.example.eventinapp.activity.MainActivity;
import com.example.eventinapp.activity.Notification;
import com.example.eventinapp.activity.RegisterActivity;
import com.example.eventinapp.adapter.CardViewCompanyAdapter;
import com.example.eventinapp.data.CompanyData;
import com.example.eventinapp.R;
import com.example.eventinapp.model.Company;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rvCompany;
    private ArrayList<Company> list = new ArrayList<>();
    TextView tvName;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.btn_notif).setOnClickListener(this);
        return view;

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tv_item_name);
        rvCompany = view.findViewById(R.id.rv_company);
        rvCompany.setHasFixedSize(true);

        list.addAll(CompanyData.getListData());
        rvCompany.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        CardViewCompanyAdapter cvCompanyAdapter = new CardViewCompanyAdapter(list);
        rvCompany.setAdapter(cvCompanyAdapter);




    }

    private void setCompany(Company company){
        tvName.setText(company.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_notif:
                startActivity(new Intent(getContext(),Notification.class));
                break;
    }
}}