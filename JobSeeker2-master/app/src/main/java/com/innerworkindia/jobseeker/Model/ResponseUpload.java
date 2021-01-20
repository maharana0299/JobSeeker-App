package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseUpload {
    @SerializedName("status")
    public String code;

    @SerializedName("message")
    public String message;

    public ResponseUpload(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
