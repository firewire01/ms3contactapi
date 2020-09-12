package com.org.ms3.contactapi.common;

import com.org.ms3.contactapi.dao.Contact;
import com.org.ms3.contactapi.dto.ContactDTO;

public interface IContactSave {
    Contact save(ContactDTO contactDTO);
}
