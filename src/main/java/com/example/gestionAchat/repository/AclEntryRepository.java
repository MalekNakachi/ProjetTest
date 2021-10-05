package com.example.gestionAchat.repository;



import com.example.gestionAchat.domain.AclEntry;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AclEntryRepository extends JpaRepository<AclEntry,Integer> {

    List<AclEntry>  findAllById(Integer id);
 //   List<AclEntry> findAclEntryByAcl_object_identityAndSidAndMask(AclObjectIdentity Aclobjectidentity, AclSid sid, Integer mask);
//    @Modifying
//   @Query("UPDATE acl_entry set mask ='1' where acl_object_identity = '123' and mask='2'")
//
//    void updateTitleById(String oid);


}
