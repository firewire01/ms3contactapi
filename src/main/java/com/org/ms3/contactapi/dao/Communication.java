package com.org.ms3.contactapi.dao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comm")
@IdClass(CommunicationId.class)
public class Communication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Id
    @Column(name = "contact_id", nullable = false)
    private long contactId;

    @Column(name = "type", nullable = false)
    @NotBlank(message = "Communication type is mandatory")
    private String type;

    @Column(name = "value", nullable = false)
    @NotBlank(message = "Communication value is mandatory")
    private String value;

    @Column(name = "preferred")
    private Boolean preferred;

    public Communication() {
    }

    public Communication(long id, long contactId, String type, String value, Boolean preferred) {
        this.id = id;
        this.contactId = contactId;
        this.type = type;
        this.value = value;
        this.preferred = preferred;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
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
        return "Communication{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", preferred=" + preferred +
                '}';
    }
}
