package com.example.gestionAchat.repository.pm;


import com.example.gestionAchat.domain.GroupUserPK;
import com.example.gestionAchat.entities.Group;
import com.example.gestionAchat.entities.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserPK>, JpaSpecificationExecutor<Group> {

    Optional<GroupUser> findByUser_Id(Long iduser);
    List<GroupUser> findByGroup_Id(Long iduser);


}
