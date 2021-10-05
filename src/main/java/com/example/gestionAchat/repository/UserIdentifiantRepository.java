package com.example.gestionAchat.repository;


import com.example.gestionAchat.domain.UserIdentifiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentifiantRepository extends JpaRepository<UserIdentifiant,Long> {

    UserIdentifiant findBySamaccountname(String sama);

}
