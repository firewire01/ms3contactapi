package com.org.ms3.contactapi.controller;

import com.org.ms3.contactapi.common.SaveImp;
import com.org.ms3.contactapi.common.UpdateImp;
import com.org.ms3.contactapi.dto.ContactDTO;
import com.org.ms3.contactapi.exception.ResourceNotFoundException;
import com.org.ms3.contactapi.services.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ContactController {

    @Autowired
    private IContactService contractService;

    @Autowired
    private SaveImp saveImp;

    @Autowired
    private UpdateImp updateImp;

    @GetMapping("/contacts")
    public List<ContactDTO> getAllContacts() {
        return contractService.listOfAllContact();
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok().body(contractService.findById(id));
    }

    @PostMapping("/contacts")
    public  ContactDTO createContact(@Valid @RequestBody ContactDTO contactDTO) {
        return contractService.save(contactDTO, saveImp);
    }

    @PutMapping("/contacts")
    public ResponseEntity<ContactDTO> updateContact(@Valid @RequestBody ContactDTO contactDTO)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(contractService.save(contactDTO, updateImp));
    }

    @DeleteMapping("/contacts/{id}")
    public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        contractService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
