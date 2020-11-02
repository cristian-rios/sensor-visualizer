package com.example.sensorregister.requestUtilities.services;

import com.example.sensorregister.requestUtilities.event.EventRequest;
import com.example.sensorregister.requestUtilities.event.EventResponse;
import com.example.sensorregister.requestUtilities.login.LoginRequest;
import com.example.sensorregister.requestUtilities.login.LoginResponse;
import com.example.sensorregister.requestUtilities.register.RegisterRequest;
import com.example.sensorregister.requestUtilities.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestService {
    @POST("api/api/register")
    @Headers({
            "content-type: application/json",
    })
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("api/api/login")
    @Headers({
            "content-type: application/json",
    })
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/api/event")
    @Headers({
            "content-type: application/json",
    })
    Call<EventResponse> event(@Header("Authorization") String bearer, @Body EventRequest eventRequest);
}
