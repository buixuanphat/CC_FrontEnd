package com.example.cloudcomputing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();

    ApiService apiService =new Retrofit.Builder().baseUrl(Static.baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);

    @Multipart
    @POST("user/")
    Call<ResponseBody> registerUser(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );

    @Multipart
    @POST("o/token/")
    Call<ResponseBody> logIn(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("client_id") RequestBody clientId,
            @Part("client_secret") RequestBody clientSecret,
            @Part("grant_type") RequestBody grantType
    );


    @GET("user/current-user/")
    Call<ResponseBody> getCurrentUser(@Header("Authorization") String token);

}
