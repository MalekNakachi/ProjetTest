package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.AuthAffectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.gestionAchat.entities.Application;


import java.util.List;

public interface AuthAffectationRepository extends JpaRepository<AuthAffectation, Long>, JpaSpecificationExecutor<AuthAffectation> {
    @Query("SELECT a FROM AuthAffectation a WHERE a.application =:application and a.status=true ORDER BY a.orders ASC ")
    List<AuthAffectation> findAllByOrder(@Param(value = "application") Application application);
}
