package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.Reference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the BonCommande entity.
 */
@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {


}
