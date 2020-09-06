package com.org.ms3.contactapi.repository;

import com.org.ms3.contactapi.dao.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {
    List<Communication> findByContactId(Long contactId);
}
