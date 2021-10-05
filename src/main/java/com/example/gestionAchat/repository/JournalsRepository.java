package com.example.gestionAchat.repository;


import com.example.gestionAchat.domain.Journals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Journals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalsRepository extends JpaRepository<Journals, Long> {

}
