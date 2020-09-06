package com.org.ms3.contactapi.repository;

import com.org.ms3.contactapi.dao.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByContactId(Long contactId);
}
