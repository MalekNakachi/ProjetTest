package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.Expand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpandRepository extends JpaRepository<Expand, Long>, JpaSpecificationExecutor<Expand> {

    List<Expand> findAllByDepartement(String departement);

    List<Expand> findAllByUserId(int user_id);
}
