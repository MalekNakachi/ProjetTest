package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.Profile;
import com.example.gestionAchat.entities.User;
import com.example.gestionAchat.entities.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.example.gestionAchat.entities.Application;

import java.util.List;
import java.util.Optional;

public interface UserAccessRepository extends JpaRepository<UserAccess, Long>, JpaSpecificationExecutor<UserAccess> {

    @Query(value = "SELECT * FROM pm_user_access WHERE application=(select id from pm_application where alias =?) and users =(select id from pm_users where samaccountname = ?)", nativeQuery = true)
    Optional<UserAccess> findUserAccessById(String application, String user);

    Optional<UserAccess> findUserAccessByApplicationAndUserAndProfile(Application application, User user, Profile profile);

  List<UserAccess> findAllByProfile(Profile profile);

}
