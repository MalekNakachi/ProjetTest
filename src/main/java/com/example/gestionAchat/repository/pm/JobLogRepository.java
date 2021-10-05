package com.example.gestionAchat.repository.pm;


import com.example.gestionAchat.entities.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JobLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobLogRepository extends JpaRepository<JobLog, Long>, JpaSpecificationExecutor<JobLog> {
}
