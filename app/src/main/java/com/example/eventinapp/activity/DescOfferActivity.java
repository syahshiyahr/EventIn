package com.example.eventinapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eventinapp.R;
import com.example.eventinapp.adapter.CardViewCompanyAdapter;
import com.example.eventinapp.data.CompanyData;
import com.example.eventinapp.model.Company;
import com.example.eventinapp.ui.home.HomeFragment;

import java.util.ArrayList;

import static android.nfc.NfcAdapter.EXTRA_DATA;

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
                Intent back = new Intent(DescOfferActivity.this, HomeFragment.class);
                startActivity(back);
                break;
            case R.id.btn_acc_offer:
                break;
            case R.id.btn_make_offer:
                break;
        }
    }
}
