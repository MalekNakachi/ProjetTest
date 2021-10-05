package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.Pv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PvRepository extends JpaRepository<Pv, Long> {

}
