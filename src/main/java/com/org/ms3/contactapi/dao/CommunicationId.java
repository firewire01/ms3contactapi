package com.org.ms3.contactapi.dao;

import java.io.Serializable;
import java.util.Objects;

public class CommunicationId implements Serializable {

    private long id;

    private long contactId;

    public CommunicationId() {
    }

    public CommunicationId(long id, long contactId) {
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
        return "CommunicationId{" +
                "id=" + id +
                ", contactId=" + contactId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommunicationId)) return false;
        CommunicationId that = (CommunicationId) o;
        return id == that.id &&
                contactId == that.contactId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactId);
    }
}
