package com.example.eventinapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventinapp.R;
import com.example.eventinapp.api.ApiClient;
import com.example.eventinapp.api.MyApi;
import com.example.eventinapp.model.Login;
import com.example.eventinapp.responses.LoginResponse;
import com.example.eventinapp.utils.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail, edtPassword;
    private String email, password;
    private MyApi myApi;
    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appPreference = new AppPreference(LoginActivity.this);


        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password_login);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        TextView register = findViewById(R.id.reg_text);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                loginUser();
                break;
            case R.id.reg_text:
                Intent toReg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toReg);
                break;
        }
    }

    private void loginUser() {
        // ngambil text dari edit text
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();

        Login login = new Login(email, password);

        myApi = ApiClient.getClient().create(MyApi.class);
        Call<LoginResponse> loginCall = myApi.login(login);

        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();

                    Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();


                    appPreference.saveToken(token);

                    //untuk berpindah activity dari parameter kiri ke parameter kanan
                    Intent intent_signin = new Intent(LoginActivity.this, CreateEventActivity.class);
                    startActivity(intent_signin);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(appPreference.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    }


