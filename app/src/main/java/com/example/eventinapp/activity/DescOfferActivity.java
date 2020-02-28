package com.example.eventinapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventinapp.R;

public class DescOfferActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textName, textDesc;
    ImageView photoCompany;
    String title, desc;
    int image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_offer);

        textName = findViewById(R.id.name_compamy_offer);
        textDesc = findViewById(R.id.sp_details);
        photoCompany = findViewById(R.id.img_desc_offer);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        image = getIntent().getIntExtra("image", 0);

        textName.setText(title);
        textDesc.setText(desc);
        photoCompany.setImageResource(image);


        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        Button btn_acc_offer = findViewById(R.id.btn_acc_offer);
        btn_acc_offer.setOnClickListener(this);

        Button btn_make_offer = findViewById(R.id.btn_make_offer);
        btn_make_offer.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_acc_offer:
                Intent acc = new Intent(DescOfferActivity.this, ChooseEventAccActivity.class);
                startActivity(acc);
                break;
            case R.id.btn_make_offer:
                Intent make = new Intent(DescOfferActivity.this, ChooseEventMakeActivity.class);
                startActivity(make);
                break;
        }
    }
}
