package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.Processus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Processus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessusRepository extends JpaRepository<Processus, Long> {

}
