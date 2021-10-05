package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    default String getManagerOfUser(String samaccountname) {
        Optional<User> user = findBySamaccountname(samaccountname);
        String distinguishedname = user.get().getDistinguishedname();
        if ((distinguishedname != null) && (distinguishedname.startsWith("CN="))) {
            String[] result = distinguishedname.split(",OU=");
            if (result.length > 1) {
                return result[1];
            }
        }
        return null;
    }

 //   @Query("SELECT u FROM User u where u.apitoken = :token")
   // Optional<User> findUserByAPItoken(@Param("token")String token);

    User findByDisplayname(String displayname);

    //@Query("SELECT u FROM User u WHERE u.memberof like %:membre%")
    List<User> findAllByMemberof(String membre);

    //@Query("SELECT t FROM User t where t.samaccountname = :username OR t.mail = :username")
   // Optional<User> findBySamaccountname(@Param("username") String username);
    Optional<User> findBySamaccountname(String username);


    Optional<User> findUserByMail(String mail);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);

    List<User> findAllByStatus(Boolean status);

    List<User> findAllByTest(Boolean test);

    Optional<User> findUserByDistinguishedname(String dn);

}
