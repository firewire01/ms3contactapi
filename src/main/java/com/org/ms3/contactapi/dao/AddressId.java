package com.org.ms3.contactapi.dao;

import java.io.Serializable;
import java.util.Objects;

public class AddressId implements Serializable {

    private long id;

    private long contactId;

    public AddressId() {
    }

    public AddressId(long id, long contactId) {
        this.id = id;
        this.contactId = contactId;
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
        return "AddressId{" +
                "id=" + id +
                ", contactId=" + contactId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressId)) return false;
        AddressId addressId = (AddressId) o;
        return id == addressId.id &&
                contactId == addressId.contactId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactId);
    }
}
