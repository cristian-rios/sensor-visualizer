package com.example.sensorregister.requestUtilities.register;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("env")
    private String env;
    @SerializedName("token")
    private String token;
    @SerializedName("token_refresh")
    private String tokenRefresh;
    @SerializedName("msg")
    private String msg;
    public String getEnv() {
        return env;
    }

    public String getTokenRefresh() {
        return tokenRefresh;
    }

    public String getToken() {
        return token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
