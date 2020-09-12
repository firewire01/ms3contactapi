package com.org.ms3.contactapi.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDTO {

    @JsonProperty(value ="id")
    private long id;

    @JsonProperty(value ="type",required = true)
    private String type;

    @JsonProperty(value ="number")
    private int number;

    @JsonProperty(value ="street",required = true)
    private String street;

    @JsonProperty(value ="unit")
    private String unit;

    @JsonProperty(value ="city",required = true)
    private String city;

    @JsonProperty(value ="state")
    private String state;

    @JsonProperty(value ="zipcode")
    private String zipcode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressDTO() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", number=" + number +
                ", street='" + street + '\'' +
                ", unit='" + unit + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
