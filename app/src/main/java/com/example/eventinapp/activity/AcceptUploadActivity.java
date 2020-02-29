package com.example.eventinapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.eventinapp.R;

public class AcceptUploadActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_upload);

        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_submit:
                Intent popup = new Intent(AcceptUploadActivity.this, PopUpActivity.class);
                startActivity(popup);
                break;
        }
    }
}
