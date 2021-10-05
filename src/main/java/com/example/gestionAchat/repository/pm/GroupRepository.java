package com.example.gestionAchat.repository.pm;


import com.example.gestionAchat.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

Group  findByName(String name);

Optional<Group>  findById(Long id);

}
