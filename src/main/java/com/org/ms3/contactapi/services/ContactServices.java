package com.org.ms3.contactapi.services;

import com.org.ms3.contactapi.common.IContactSave;
import com.org.ms3.contactapi.dao.*;
import com.org.ms3.contactapi.dto.AddressDTO;
import com.org.ms3.contactapi.dto.CommunicationDTO;
import com.org.ms3.contactapi.dto.ContactDTO;
import com.org.ms3.contactapi.exception.ResourceNotFoundException;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServices extends BaseService
        implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public ContactDTO save(ContactDTO contactDTO, IContactSave iContactSave) {
        return contactToContactDTO(iContactSave.save(contactDTO), contactDTO);
    }

    @Transactional
    @Override
    public ContactDTO findById(long id) throws ResourceNotFoundException{
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        return contactToContactDTO(contact, new ContactDTO());
    }

    @Transactional
    @Override
    public void delete(ContactDTO contactDTO) {
        Contact contact = new Contact();
        modelMapper.map(contact, contactDTO.getIdentificationDTO());

        contactRepository.delete(contact);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        contactRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<ContactDTO> listOfAllContact() {
        List<ContactDTO> contactDTOS;

        List<Contact> contactList = contactRepository.findAll();

        contactDTOS = contactList.stream().map(
                c -> contactToContactDTO(c, new ContactDTO())
        ).collect(Collectors.toList());

        return contactDTOS;
    }

    /**
     * Helper method for contract To contract DTO
     *
     * @param contact
     * @param contactDTO
     * @return
     */
    private ContactDTO contactToContactDTO(Contact contact, ContactDTO contactDTO){

        modelMapper.map(contact, contactDTO.getIdentificationDTO());
        List<Address> addressList = addressRepository.findByContactId(contact.getId());
        List<Communication> communicationSet =
                communicationRepository.findByContactId(contact.getId());

        contactDTO.setAddressDTOList(
                addressList.stream().map(a ->
                {
                    AddressDTO addressDTO =
                            new AddressDTO();
                    BeanUtils.copyProperties(a, addressDTO);
                    return addressDTO;
                }).collect(Collectors.toList())
        );

        contactDTO.setCommunicationDTOList(
                communicationSet.stream().map(com ->
                {
                    CommunicationDTO communicationDTO =
                            new CommunicationDTO();
                    BeanUtils.copyProperties(com, communicationDTO);
                    return communicationDTO;
                }).collect(Collectors.toList())
        );

        return contactDTO;
    }


}
