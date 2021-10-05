package com.example.gestionAchat.repository;




import com.example.gestionAchat.domain.AclClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AclClassRepository extends JpaRepository<AclClass,Integer> {

    List<AclClass>  findAllById(Integer id);
   Optional<AclClass> findByClasse(String classe);

}
