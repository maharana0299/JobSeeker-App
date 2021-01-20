package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class BodyGetUserApplications {

    @SerializedName("userId")
    public int userId;

    @SerializedName("type")
    public String type;

    public BodyGetUserApplications(int userId, String type) {
        this.userId = userId;
        this.type = type;
    }
}
