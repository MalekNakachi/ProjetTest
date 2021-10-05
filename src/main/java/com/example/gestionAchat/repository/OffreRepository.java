package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Offre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Offre> findAllByDemandeAchat_Id(Long id);
 List<Offre> findAllByFournisseur_Id(Long id);
    List<Offre> findAllByBonCommande_Id(Long id);
}
