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

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the save implementation
 */
@Component
public class SaveImp implements IContactSave{

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

        List<Address> addressList = contactDTO
                .getAddressDTOList().stream().map(addressDTO ->
                {
                    Address address = new Address();
                    BeanUtils.copyProperties(addressDTO, address);
                    return address;
                }).collect(Collectors.toList());

        List<Communication> communicationList = contactDTO
                .getCommunicationDTOList().stream().map(s ->
                {
                    Communication communication = new Communication();
                    BeanUtils.copyProperties(s, communication);
                    return communication;
                }).collect(Collectors.toList());

        contact = contactRepository.save(contact);
        final Long contactId = contact.getId();
        addressList.stream().forEach(a -> a.setContactId(contactId));
        communicationList.stream().forEach(a -> a.setContactId(contactId));


        addressRepository.saveAll(addressList);
        communicationRepository.saveAll(communicationList);

        return contact;
    }
}