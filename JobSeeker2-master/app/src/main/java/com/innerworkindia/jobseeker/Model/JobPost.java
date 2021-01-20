package com.innerworkindia.jobseeker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobPost {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("jobTitle")
    @Expose
    public String jobTitle;
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("jobType")
    @Expose
    public String jobType;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("minSalary")
    @Expose
    public String minSalary;
    @SerializedName("maxSalary")
    @Expose
    public String maxSalary;
    @SerializedName("cpname")
    @Expose
    public String cpname;
    @SerializedName("cpnum")
    @Expose
    public String cpnum;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("exp")
    @Expose
    public String exp;
    @SerializedName("education")
    @Expose
    public String education;
    @SerializedName("j_desc")
    @Expose
    public String jDesc;
    @SerializedName("dateTime")
    @Expose
    public String dateTime;
    @SerializedName("job_referalamt")
    @Expose
    public int jobReferalamt;
    @SerializedName("about_comp")
    @Expose
    public String aboutComp;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("skills")
    @Expose
    public String skills;
    @SerializedName("idesc")
    @Expose
    public String idesc;
    @SerializedName("agency_id")
    @Expose
    public String agencyId;

    /**
     * No args constructor for use in serialization
     *
     */
    public JobPost() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getCpnum() {
        return cpnum;
    }

    public void setCpnum(String cpnum) {
        this.cpnum = cpnum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getjDesc() {
        return jDesc;
    }

    public void setjDesc(String jDesc) {
        this.jDesc = jDesc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getJobReferalamt() {
        return jobReferalamt;
    }

    public void setJobReferalamt(int jobReferalamt) {
        this.jobReferalamt = jobReferalamt;
    }

    public String getAboutComp() {
        return aboutComp;
    }

    public void setAboutComp(String aboutComp) {
        this.aboutComp = aboutComp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getIdesc() {
        return idesc;
    }

    public void setIdesc(String idesc) {
        this.idesc = idesc;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    /**
     *
     * @param dateTime
     * @param maxSalary
     * @param aboutComp
     * @param education
     * @param jobReferalamt
     * @param jobTitle
     * @param agencyId
     * @param minSalary
     * @param type
     * @param idesc
     * @param jDesc
     * @param skills
     * @param cpname
     * @param company
     * @param location
     * @param id
     * @param jobType
     * @param exp
     * @param email
     * @param cpnum
     * @param status
     */
    public JobPost(String id, String jobTitle, String company, String email, String jobType, String location, String minSalary, String maxSalary, String cpname, String cpnum, String status, String exp, String education, String jDesc, String dateTime, int jobReferalamt, String aboutComp, String type, String skills, String idesc, String agencyId) {
        super();
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.email = email;
        this.jobType = jobType;
        this.location = location;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.cpname = cpname;
        this.cpnum = cpnum;
        this.status = status;
        this.exp = exp;
        this.education = education;
        this.jDesc = jDesc;
        this.dateTime = dateTime;
        this.jobReferalamt = jobReferalamt;
        this.aboutComp = aboutComp;
        this.type = type;
        this.skills = skills;
        this.idesc = idesc;
        this.agencyId = agencyId;
    }

}