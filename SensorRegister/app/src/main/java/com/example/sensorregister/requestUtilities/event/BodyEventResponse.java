package com.example.sensorregister.requestUtilities.event;

import com.google.gson.annotations.SerializedName;

public class BodyEventResponse {
    @SerializedName("type_events")
    private String typeEvents;
    @SerializedName("dni")
    private long dni;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private long id;
}