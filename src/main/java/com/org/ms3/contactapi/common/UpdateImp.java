package com.org.ms3.contactapi.common;


import com.org.ms3.contactapi.dao.Address;
import com.org.ms3.contactapi.dao.Communication;
import com.org.ms3.contactapi.dao.Contact;
import com.org.ms3.contactapi.dto.ContactDTO;
import com.org.ms3.contactapi.repository.AddressRepository;
import com.org.ms3.contactapi.repository.CommunicationRepository;
import com.org.ms3.contactapi.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        contact = contactRepository.save(contact);
        final Long contactId = contact.getId();

        //update address
        List<Address> addressList = contactDTO
                .getAddressDTOList().stream().map(addressDTO ->
                {
                    Address address = new Address();
                    BeanUtils.copyProperties(addressDTO, address);
                    address.setContactId(contactId);
                    return address;
                }).collect(Collectors.toList());

        List<Address> currentAddress = addressRepository
                .findByContactId(contact.getId());

        List<Address> toDelete = currentAddress.stream().filter(
                ca -> !addressList.stream().anyMatch(adto ->
                        adto.getId() == ca.getId())
        ).collect(Collectors.toList());

        addressRepository.deleteAll(toDelete);
        addressRepository.saveAll(addressList);

        //delete communication
        List<Communication> communicationList = contactDTO
                .getCommunicationDTOList().stream().map(comDTO ->
                {
                    Communication communication = new Communication();
                    BeanUtils.copyProperties(comDTO, communication);
                    communication.setContactId(contactId);
                    return communication;
                }).collect(Collectors.toList());

        List<Communication> currentComms = communicationRepository
                .findByContactId(contact.getId());

        List<Communication> comstoDelete = currentComms.stream().filter(
                ca -> !communicationList.stream().anyMatch(cdto ->
                        cdto.getId() == ca.getId())
        ).collect(Collectors.toList());

        communicationRepository.deleteAll(comstoDelete);

        communicationRepository.saveAll(communicationList);

        return contact;
    }
}