package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.example.gestionAchat.entities.Department;


import java.util.List;
import java.util.Optional;

@Repository
public interface DepRepository extends JpaRepository<com.example.gestionAchat.entities.Department, Long>, JpaSpecificationExecutor<com.example.gestionAchat.entities.Department> {
    Optional<Department> findByName(String name);

    Optional<Department> findByDistinguishedname(String distinguishedname);

    List<Department> findAllByStatus(Boolean status);

    List<Department> findAllByManagedby(String managerBy);

    List<Department> findAllByTest(Boolean test);

    List<Department> findAllByInterim(String interim);
}
