package com.example.sensorregister.requestUtilities.register;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("env")
    private String env;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("dni")
    private long dni;
    @SerializedName("commission")
    private long commission;

    public RegisterRequest(String email, String password, String name, String lastname, long dni, long commission, String env){
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.commission = commission;
        this.env = env;
    }

    public long getCommission() {
        return commission;
    }

    public long getDni() {
        return dni;
    }

    public String getEnv() {
        return env;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setCommission(long commission) {
        this.commission = commission;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
