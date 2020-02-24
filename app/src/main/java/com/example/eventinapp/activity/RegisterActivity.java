package com.example.eventinapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eventinapp.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView toSignin = findViewById(R.id.to_sign_text);
        toSignin.setOnClickListener(this);

        Button register = findViewById(R.id.btn_register);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_sign_text:
                Intent toSign = new Intent (RegisterActivity.this, LoginActivity.class);
                startActivity(toSign);
                break;
            case R.id.btn_register:
                Intent reg = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(reg);
                break;
    }
}}
