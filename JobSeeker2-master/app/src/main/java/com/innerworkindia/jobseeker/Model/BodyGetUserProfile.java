package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class BodyGetUserProfile {

    @SerializedName("userId")
    public Integer userId;

    public BodyGetUserProfile(Integer userId) {
        this.userId = userId;
    }
}
