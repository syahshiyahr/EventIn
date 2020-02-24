package com.example.eventinapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventinapp.adapter.CardViewCompanyAdapter;
import com.example.eventinapp.data.CompanyData;
import com.example.eventinapp.R;
import com.example.eventinapp.model.Company;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_home, container, false);

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
}