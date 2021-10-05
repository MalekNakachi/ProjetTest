package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.BonCommande;
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
public interface BonCommandeRepository extends JpaRepository<BonCommande, Long> {

    @Query(value = "select distinct bonCommande from BonCommande bonCommande left join fetch bonCommande.articles",
        countQuery = "select count(distinct bonCommande) from BonCommande bonCommande")
    Page<BonCommande> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct bonCommande from BonCommande bonCommande left join fetch bonCommande.articles")
    List<BonCommande> findAllWithEagerRelationships();

    @Query("select bonCommande from BonCommande bonCommande left join fetch bonCommande.articles where bonCommande.id =:id")
    Optional<BonCommande> findOneWithEagerRelationships(@Param("id") Long id);

}
