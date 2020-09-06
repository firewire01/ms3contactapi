package com.org.ms3.contactapi.services;

import com.org.ms3.contactapi.dto.ContactDTO;
import com.org.ms3.contactapi.exception.ResourceNotFoundException;

import java.util.List;

public interface IContactService {

    ContactDTO save(ContactDTO contactDTO);
    ContactDTO findById(long id) throws ResourceNotFoundException;
    void delete(ContactDTO contactDTO);
    void deleteById(long id);
    List<ContactDTO> listOfAllContact();
}
