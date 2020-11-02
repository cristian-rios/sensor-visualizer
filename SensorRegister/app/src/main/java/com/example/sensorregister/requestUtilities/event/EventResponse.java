package com.example.sensorregister.requestUtilities.event;

import com.google.gson.annotations.SerializedName;

public class EventResponse {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("token")
    private String token;
    @SerializedName("token_refresh")
    private String tokenRefresh;
    @SerializedName("msg")
    private String msg;
    @SerializedName("event")
    private BodyEventResponse event;

    public Boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getTokenRefresh() {
        return tokenRefresh;
    }

    public String getMsg() {
        return msg;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BodyEventResponse getEvent() {
        return event;
    }

    public void setEvent(BodyEventResponse event) {
        this.event = event;
    }

}
