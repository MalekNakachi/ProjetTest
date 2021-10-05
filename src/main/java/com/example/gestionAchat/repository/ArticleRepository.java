package com.example.gestionAchat.repository;

import com.example.gestionAchat.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByDemandeAchat_Id(Long id);
}
