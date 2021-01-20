package com.innerworkindia.jobseeker.Model;

public class Internship {

    int image;
    String title,name,stipend,location,duration,jobOrInternship;

    public Internship(int image, String title, String name, String stipend, String location, String duration, String jobOrInternship) {
        this.image = image;
        this.title = title;
        this.name = name;
        this.stipend = stipend;
        this.location = location;
        this.duration = duration;
        this.jobOrInternship = jobOrInternship;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStipend() {
        return stipend;
    }
    public void setStipend(String stipend) {
        this.stipend = stipend;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getJobOrInternship() {
        return jobOrInternship;
    }
    public void setJobOrInternship(String jobOrInternship) {
        this.jobOrInternship = jobOrInternship;
    }
}
