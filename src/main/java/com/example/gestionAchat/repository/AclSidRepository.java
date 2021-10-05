package com.example.gestionAchat.repository;



import com.example.gestionAchat.domain.AclSid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface AclSidRepository extends JpaRepository<AclSid,Integer> {

    List<AclSid>  findAllById(Integer id);
    List<AclSid>  findAllBySid(String sid);
    AclSid  findBySid(String sid);


}


