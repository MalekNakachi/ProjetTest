package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.DemandeAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DemandeAchat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeAchatRepository extends JpaRepository<DemandeAchat, Long> {


}
