package com.org.ms3.contactapi.dao;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@IdClass(AddressId.class)
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Id
    @Column(name = "contact_id", nullable = false)
    private long contactId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "number")
    private int number;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "unit")
    private String unit;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    public Address() {
    }

    public Address(long id, long contactId, String type, int number, String street,
                   String unit, String state, String city, String zipcode) {
        this.id = id;
        this.contactId = contactId;
        this.type = type;
        this.number = number;
        this.street = street;
        this.unit = unit;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", type='" + type + '\'' +
                ", number=" + number +
                ", street='" + street + '\'' +
                ", unit='" + unit + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
