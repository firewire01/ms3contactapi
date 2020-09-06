package com.org.ms3.contactapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ContactDTO {

    @JsonProperty("Identification")
    private IdentificationDTO identificationDTO;

    @JsonProperty("Address")
    private List<AddressDTO> addressDTOList;

    @JsonProperty("Communication")
    private List<CommunicationDTO> communicationDTOList;

    public ContactDTO() {
        identificationDTO = new IdentificationDTO();
        this.addressDTOList = new ArrayList<>();
        this.communicationDTOList = new ArrayList<>();
    }

    public ContactDTO(IdentificationDTO identificationDTO,
                      List<AddressDTO> addressDTOList, List<CommunicationDTO> communicationDTOList) {
        this.identificationDTO = identificationDTO;
        this.addressDTOList = addressDTOList;
        this.communicationDTOList = communicationDTOList;
    }

    public IdentificationDTO getIdentificationDTO() {
        return identificationDTO;
    }

    public void setIdentificationDTO(IdentificationDTO identificationDTO) {
        this.identificationDTO = identificationDTO;
    }

    public List<AddressDTO> getAddressDTOList() {
        return addressDTOList;
    }

    public void setAddressDTOList(List<AddressDTO> addressDTOList) {
        this.addressDTOList = addressDTOList;
    }

    public List<CommunicationDTO> getCommunicationDTOList() {
        return communicationDTOList;
    }

    public void setCommunicationDTOList(List<CommunicationDTO> communicationDTOList) {
        this.communicationDTOList = communicationDTOList;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "identification=" + identificationDTO +
                ", addressList=" + addressDTOList +
                ", communicationList=" + communicationDTOList +
                '}';
    }
}
