package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.JournalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JournalDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalDetailsRepository extends JpaRepository<JournalDetails, Long> {

}
