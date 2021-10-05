package com.example.gestionAchat.service.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.JobLog;
import com.example.gestionAchat.repository.pm.JobLogRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobLog}.
 */
@Service
@Transactional
public class JobLogService {

    private final Logger log = LoggerFactory.getLogger(JobLogService.class);

    private final JobLogRepository jobLogRepository;

    public JobLogService(JobLogRepository jobLogRepository) {
        this.jobLogRepository = jobLogRepository;
    }

    /**
     * Save a jobLog.
     *
     * @param jobLog the entity to save.
     * @return the persisted entity.
     */
    public JobLog save(JobLog jobLog) {
        log.debug("Request to save JobLog : {}", jobLog);
        return jobLogRepository.save(jobLog);
    }

    /**
     * Get all the jobLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobLog> findAll(Pageable pageable) {
        log.debug("Request to get all JobLogs");
        return jobLogRepository.findAll(pageable);
    }


    /**
     * Get one jobLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobLog> findOne(Long id) {
        log.debug("Request to get JobLog : {}", id);
        return jobLogRepository.findById(id);
    }

    /**
     * Delete the jobLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobLog : {}", id);
        jobLogRepository.deleteById(id);
    }
}
