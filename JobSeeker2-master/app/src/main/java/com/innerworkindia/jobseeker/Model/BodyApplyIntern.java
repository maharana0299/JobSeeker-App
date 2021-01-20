package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyApplyIntern {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("education")
    @Expose
    public String education;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone")
    @Expose
    public Long phone;
    @SerializedName("skill")
    @Expose
    public String skill;
    @SerializedName("interest")
    @Expose
    public String interest;
    @SerializedName("exp")
    @Expose
    public String exp;
    @SerializedName("jppid")
    @Expose
    public Long jppid;
    @SerializedName("userId")
    @Expose
    public Long userId;

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param education
     * @param gender
     * @param city
     * @param phone
     * @param interest
     * @param skill
     * @param name
     * @param exp
     * @param userId
     * @param email
     * @param jppid
     */
    public BodyApplyIntern(String name, String gender, String city, String education, String email, Long phone, String skill, String interest, String exp, Long jppid, Long userId) {
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.education = education;
        this.email = email;
        this.phone = phone;
        this.skill = skill;
        this.interest = interest;
        this.exp = exp;
        this.jppid = jppid;
        this.userId = userId;
    }

}
