package com.org.ms3.contactapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class IdentificationDTO {


    @JsonProperty(value ="id")
    private long id;

    @JsonProperty(value ="firstName",required = true)
    private String firstName;

    @JsonProperty(value ="lastName",required = true)
    private String lastName;

    @JsonFormat(pattern="MM/dd/yyyy")
    @JsonProperty(value ="dob",required = true)
    private Date dob;

    @JsonProperty(value ="gender",required = true)
    private String gender;

    @JsonProperty(value ="title",required = true)
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Identification{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
