package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class BodySignUpUser {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("password")
    private String password;

    @SerializedName("isIntern")
    private int isIntern;

    @SerializedName("isJob")
    private int isJob;

    @SerializedName("location")
    private String location;

    public BodySignUpUser(String name, String email, String phone, String password, int isIntern, int isJob, String location) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isIntern = isIntern;
        this.isJob = isJob;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public int getIsIntern() {
        return isIntern;
    }

    public int getIsJob() {
        return isJob;
    }

    public String getLocation() {
        return location;
    }
}
