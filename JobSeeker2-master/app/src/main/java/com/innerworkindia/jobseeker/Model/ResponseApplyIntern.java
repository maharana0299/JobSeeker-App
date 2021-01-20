package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseApplyIntern {

    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseApplyIntern(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
