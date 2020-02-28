package com.example.eventinapp.api;


import com.example.eventinapp.model.Login;
import com.example.eventinapp.model.User;
import com.example.eventinapp.responses.BaseResponse;
import com.example.eventinapp.responses.LoginResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
public interface MyApi {
    //USER
    //register
    @Multipart
    @POST("User/register")
    Call<BaseResponse> register(
            @Part("name") String name,
            @Part("email") String email,
            @Part("password") String password,
            @Part("phoneNum") String phoneNum,
            @Part MultipartBody.Part file
    );

    //Login
    @POST("user/login")
    Call<LoginResponse> login(
            @Body
                    Login login
    );



}
