package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGetJobs {

    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    @SerializedName("jobposts")
    public ArrayList<JobPost> jobposts;

    public int getCode() {
        return code;
    }

    public ResponseGetJobs(int code, String message, ArrayList<JobPost> jobposts) {
        this.code = code;
        this.message = message;
        this.jobposts = jobposts;
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

    public ArrayList<JobPost> getJobposts() {
        return jobposts;
    }

    public void setJobposts(ArrayList<JobPost> jobposts) {
        this.jobposts = jobposts;
    }
}
