package com.example.gestionAchat.repository.pm;


import com.example.gestionAchat.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.gestionAchat.entities.Application;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    List<Role> findAllByStatus(Boolean status);

    List<Role> findAllByTest(Boolean test);

    Optional<Role> findByApplicationAndRolename(Application app, String name);

  Optional<Role> findByRolename(String name);

}
