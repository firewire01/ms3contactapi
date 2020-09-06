package com.org.ms3.contactapi.services;

import com.org.ms3.contactapi.dao.*;
import com.org.ms3.contactapi.dto.AddressDTO;
import com.org.ms3.contactapi.dto.CommunicationDTO;
import com.org.ms3.contactapi.dto.ContactDTO;
import com.org.ms3.contactapi.exception.ResourceNotFoundException;
import com.org.ms3.contactapi.repository.AddressRepository;
import com.org.ms3.contactapi.repository.CommunicationRepository;
import com.org.ms3.contactapi.repository.ContactRepository;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public ContactDTO save(ContactDTO contactDTO) {
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

        System.out.println("Contract: " + ToStringBuilder.reflectionToString(contact,
                ToStringStyle.SIMPLE_STYLE));
        System.out.println("Contract DTO: " + ToStringBuilder.reflectionToString(contactDTO,
                ToStringStyle.SIMPLE_STYLE));

        contact = contactRepository.save(contact);
        final Long contactId = contact.getId();
        addressList.stream().forEach(a -> a.setContactId(contactId));
        communicationList.stream().forEach(a -> a.setContactId(contactId));

        System.out.println("List Address:");
        for (Address a:
        addressList) {
            System.out.println("Address: " + ToStringBuilder.reflectionToString(a,
                    ToStringStyle.SIMPLE_STYLE));
        }

        System.out.println("List Communication:");
        for (Communication a:
                communicationList) {
            System.out.println("Communication: " + ToStringBuilder.reflectionToString(a,
                    ToStringStyle.SIMPLE_STYLE));
        }

        addressRepository.saveAll(addressList);
        communicationRepository.saveAll(communicationList);

        contactDTO = contactToContactDTO(contact, contactDTO);
        return contactDTO;
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

        System.out.println("list: \\n" +
                ToStringBuilder.reflectionToString(contactList, ToStringStyle.SIMPLE_STYLE));

        contactDTOS = contactList.stream().map(
                c -> contactToContactDTO(c, new ContactDTO())
        ).collect(Collectors.toList());

        return contactDTOS;
    }

    private ContactDTO contactToContactDTO(Contact contact, ContactDTO contactDTO){

        System.out.println(
                ToStringBuilder.reflectionToString(contact, ToStringStyle.SIMPLE_STYLE));

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
                    System.out.println(a);
                    System.out.println(addressDTO);
                    return addressDTO;
                }).collect(Collectors.toList())
        );

        contactDTO.setCommunicationDTOList(
                communicationSet.stream().map(com ->
                {
                    CommunicationDTO communicationDTO =
                            new CommunicationDTO();
                    BeanUtils.copyProperties(com, communicationDTO);
                    System.out.println(com);
                    System.out.println(communicationDTO);
                    return communicationDTO;
                }).collect(Collectors.toList())
        );

        System.out.println(
                ToStringBuilder.reflectionToString(contactDTO, ToStringStyle.SIMPLE_STYLE));
        System.out.println(
                ToStringBuilder.reflectionToString(addressList, ToStringStyle.SIMPLE_STYLE));
        System.out.println(
                ToStringBuilder.reflectionToString(communicationSet, ToStringStyle.SIMPLE_STYLE));

        return contactDTO;
    }
}
