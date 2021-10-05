package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.gestionAchat.entities.Application;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {
    List<Profile> findByApplication(Application application);

    Profile findByProfilename(String profilename);


    List<Profile> findAllByStatus(Boolean status);

    List<Profile> findAllByTest(Boolean test);

    Optional<Profile> findByApplicationAndProfilename(Application application, String name);
}
