package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class BodySendPassword {
    @SerializedName("email")
    private String email;

    public BodySendPassword(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
