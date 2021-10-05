package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.ConfigLdap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
;

@Repository
public interface LdapConfigRepository extends JpaRepository<ConfigLdap, Long>, JpaSpecificationExecutor<ConfigLdap> {

    ConfigLdap findByName(String name);

    ConfigLdap findBybase(String base);
}
