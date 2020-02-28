package com.example.eventinapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventinapp.R;
import com.example.eventinapp.activity.EditProfileActivity;
import com.example.eventinapp.adapter.CardViewCompanyAdapter;
import com.example.eventinapp.adapter.CardViewYourEventAdapter;
import com.example.eventinapp.data.CompanyData;
import com.example.eventinapp.data.EventData;
import com.example.eventinapp.model.Company;
import com.example.eventinapp.model.Event;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    TextView edtProfil, tvName;
    private RecyclerView rvEvent;
    private ArrayList<Event> list = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtProfil = view.findViewById(R.id.edt_profil);
        edtProfil.setOnClickListener(this);

        tvName = view.findViewById(R.id.tv_item_name);
        rvEvent = view.findViewById(R.id.rv_event_profile);
        rvEvent.setHasFixedSize(true);

        list.addAll(EventData.getListData());
//        Toast.makeText(getActivity(), String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
//        Log.d("jumlah data: ", String.valueOf(list.size()));
        Log.d("tesss : ", "onViewCreated: " + String.valueOf(list.size()));
        rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        CardViewYourEventAdapter cvEventAdapter = new CardViewYourEventAdapter(list);
        rvEvent.setAdapter(cvEventAdapter);




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edt_profil:
                startActivity(new Intent(getContext(), EditProfileActivity.class));
                break;
        }
    }
}
