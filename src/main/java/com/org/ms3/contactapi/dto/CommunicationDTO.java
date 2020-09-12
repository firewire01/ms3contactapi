package com.org.ms3.contactapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CommunicationDTO {

    @JsonProperty(value ="id")
    private long id;

    @JsonProperty(value ="type",required = true)
    private String type;

    @JsonProperty(value ="value",required = true)
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
