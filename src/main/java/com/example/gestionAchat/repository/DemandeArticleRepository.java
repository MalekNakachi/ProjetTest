//package com.example.gestionAchat.repository;
//
//
//
//import com.example.gestionAchat.domain.DemandeAchat;
//import com.example.gestionAchat.domain.GroupUserPK;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface DemandeArticleRepository extends JpaRepository<demande_article, GroupUserPK>, JpaSpecificationExecutor<DemandeAchat> {
//
//    Optional<demande_article> findByDemande_Id(Long iduser);
//    List<article> findByArticleId(Long iduser);
//
//
//}
