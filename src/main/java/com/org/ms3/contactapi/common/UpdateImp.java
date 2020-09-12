package com.org.ms3.contactapi.common;


import com.org.ms3.contactapi.dao.Address;
import com.org.ms3.contactapi.dao.Communication;
import com.org.ms3.contactapi.dao.Contact;
import com.org.ms3.contactapi.dto.ContactDTO;
import com.org.ms3.contactapi.repository.AddressRepository;
import com.org.ms3.contactapi.repository.CommunicationRepository;
import com.org.ms3.contactapi.repository.ContactRepository;
import jdk.jfr.internal.LogLevel;
import jdk.jfr.internal.LogTag;
import jdk.jfr.internal.Logger;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the update implementation
 */
@Component
public class UpdateImp implements IContactSave {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Contact save(ContactDTO contactDTO) {
        Contact contact = new Contact();

        modelMapper.map(contactDTO.getIdentificationDTO(), contact);

        List<Address> addressListToRemove = addressRepository
                .findByContactId(contact.getId());

        List<Address> addressList = contactDTO
                .getAddressDTOList().stream().map(addressDTO ->
                {
                    Address address = new Address();
                    BeanUtils.copyProperties(addressDTO, address);
                    return address;
                }).collect(Collectors.toList());


        Iterator<Address> additr = addressListToRemove.iterator();

        while (additr.hasNext()) {
            boolean found = false;
            for (Address add: addressList) {
                if(add.getId() == additr.next().getId()){
                    found = true;
                }
            }
            if(!found){
                additr.remove();
            }
        }

        List<Communication> communicationList = contactDTO
                .getCommunicationDTOList().stream().map(s ->
                {
                    Communication communication = new Communication();
                    BeanUtils.copyProperties(s, communication);
                    return communication;
                }).collect(Collectors.toList());

        List<Communication> commListToRemove = communicationRepository
                .findByContactId(contact.getId());

        Iterator<Communication> commItr = commListToRemove.iterator();

        while (commItr.hasNext()) {
            boolean found = false;
            for (Communication communication: communicationList) {
                if(communication.getId() == commItr.next().getId()){
                    found = true;
                }
            }
            if(!found){
                commItr.remove();
            }
        }

        contact = contactRepository.save(contact);
        final Long contactId = contact.getId();
        addressList.stream().forEach(a -> a.setContactId(contactId));
        communicationList.stream().forEach(a -> a.setContactId(contactId));

        addressRepository.deleteAll(addressListToRemove);
        communicationRepository.deleteAll(commListToRemove);

        addressRepository.saveAll(addressList);
        communicationRepository.saveAll(communicationList);

        return contact;
    }
}