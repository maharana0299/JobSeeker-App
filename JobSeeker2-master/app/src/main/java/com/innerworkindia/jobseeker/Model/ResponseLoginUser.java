package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseLoginUser {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userId")
    private String userId;

    @SerializedName("token")
    private String token;

    public ResponseLoginUser(int code, String message, String userId, String token) {
        this.code = code;
        this.message = message;
        this.userId = userId;
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
