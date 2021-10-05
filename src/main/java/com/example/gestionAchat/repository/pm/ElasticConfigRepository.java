package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.ConfigElastic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.example.gestionAchat.entities.Application;

@Repository
public interface ElasticConfigRepository extends JpaRepository<ConfigElastic, Long>, JpaSpecificationExecutor<ConfigElastic> {
    ConfigElastic findByApplication(Application app);

    ConfigElastic findByHosturl(String hosturl);
}
