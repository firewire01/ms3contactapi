package com.org.ms3.contactapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CommunicationDTO {

    private long id;

    private String type;

    private String value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean preferred;

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

    public CommunicationDTO() {
    }

    @Override
    public String toString() {
        return "Communication{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", preferred=" + preferred +
                '}';
    }
}
