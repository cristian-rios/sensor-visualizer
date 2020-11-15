package com.example.sensorregister.requestUtilities.event;

import com.google.gson.annotations.SerializedName;

public class EventRequest {
    @SerializedName("env")
    private String env;
    @SerializedName("type_events")
    private String typeEvents;
    @SerializedName("description")
    private String description;

    public EventRequest(String env, String typeEvents, String description){
        this.env = env;
        this.typeEvents = typeEvents;
        this.description = description;
    }

    public String getEnv() {
        return env;
    }

    public String getDescription() {
        return description;
    }

    public String getTypeEvents() {
        return typeEvents;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeEvents(String typeEvents) {
        this.typeEvents = typeEvents;
    }
}
