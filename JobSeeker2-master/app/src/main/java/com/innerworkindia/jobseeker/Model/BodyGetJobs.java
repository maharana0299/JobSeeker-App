package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class BodyGetJobs {

    @SerializedName("type")
    public String type;

    public BodyGetJobs(String type) {
        this.type = type;
    }
}
