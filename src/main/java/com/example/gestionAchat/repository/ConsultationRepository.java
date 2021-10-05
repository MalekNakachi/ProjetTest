package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Consultation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
