package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VarRepository extends JpaRepository<Variable, Long>, JpaSpecificationExecutor<Variable> {
    List<Variable> findAllByStatus(Boolean status);

    List<Variable> findAllByTest(Boolean test);
}
