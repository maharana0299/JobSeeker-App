package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseSignUpUser {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public ResponseSignUpUser(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
