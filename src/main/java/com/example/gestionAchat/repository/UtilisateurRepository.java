package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.Profil;
import com.example.gestionAchat.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Utilisateur entity.
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query(value = "select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.groups",
        countQuery = "select count(distinct utilisateur) from Utilisateur utilisateur")
    Page<Utilisateur> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.groups")
    List<Utilisateur> findAllWithEagerRelationships();

    @Query("select utilisateur from Utilisateur utilisateur left join fetch utilisateur.groups where utilisateur.id =:id")
    Optional<Utilisateur> findOneWithEagerRelationships(@Param("id") Long id);

    List<Utilisateur> findAllByProfil(Profil profil);
}
