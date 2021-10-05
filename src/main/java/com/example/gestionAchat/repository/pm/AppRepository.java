package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface AppRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {

    Optional<Application> findByAlias(String alias);

    List<Application> findAllByStatus(Boolean status);

    List<Application> findAllByTest(Boolean test);
}
