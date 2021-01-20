package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseGetUserProfile {

    @SerializedName("userId")
    public String userId;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("phone")
    public String phone;

    @SerializedName("location")
    public String location;

    @SerializedName("isIntern")
    public Boolean isIntern;

    @SerializedName("isJob")
    public Boolean isJob;

    @SerializedName("photo_present")
    public boolean photo_uploaded;

    @SerializedName("photo")
    public String photo;

    @SerializedName("resume_present")
    public boolean resume_uploaded;

    @SerializedName("resume")
    public String resume;


    public boolean isPhoto_uploaded() {
        return photo_uploaded;
    }

    public void setPhoto_uploaded(boolean photo_uploaded) {
        this.photo_uploaded = photo_uploaded;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isResume_uploaded() {
        return resume_uploaded;
    }

    public void setResume_uploaded(boolean resume_uploaded) {
        this.resume_uploaded = resume_uploaded;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIntern() {
        return isIntern;
    }

    public void setIntern(Boolean intern) {
        isIntern = intern;
    }

    public Boolean getJob() {
        return isJob;
    }

    public void setJob(Boolean job) {
        isJob = job;
    }

    public ResponseGetUserProfile(String userId, String name, String email, String phone, String location, Boolean isIntern, Boolean isJob, boolean photo_uploaded, String photo, boolean resume_uploaded, String resume) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.isIntern = isIntern;
        this.isJob = isJob;
        this.photo_uploaded = photo_uploaded;
        this.photo = photo;
        this.resume_uploaded = resume_uploaded;
        this.resume = resume;
    }
}
