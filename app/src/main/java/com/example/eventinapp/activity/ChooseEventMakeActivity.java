package com.example.eventinapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventinapp.R;
import com.example.eventinapp.adapter.CardViewEventAccAdapter;
import com.example.eventinapp.data.EventData;
import com.example.eventinapp.model.Event;

import java.util.ArrayList;

public class ChooseEventMakeActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvEvent;
    private ArrayList<Event> list = new ArrayList<>();
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event);

        tvName = findViewById(R.id.tv_name_event);
        rvEvent = findViewById(R.id.rv_event);
        rvEvent.setHasFixedSize(true);

        list.addAll(EventData.getListData());
        rvEvent.setLayoutManager(new LinearLayoutManager(this));
        CardViewEventAccAdapter cvEventAdapter = new CardViewEventAccAdapter(list);
        rvEvent.setAdapter(cvEventAdapter);

        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
