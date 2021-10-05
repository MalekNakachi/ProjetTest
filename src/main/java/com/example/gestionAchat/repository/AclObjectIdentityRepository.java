package com.example.gestionAchat.repository;



import com.example.gestionAchat.domain.AclObjectIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity,Integer> {

    List<AclObjectIdentity>  findAllById(Integer id);
    AclObjectIdentity  findByObjectIdentity(Long ObjectIdentity);
    }
