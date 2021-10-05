package com.example.gestionAchat.web.rest.pm;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.gestionAchat.web.rest.errors.BadRequestAlertException;
import com.example.gestionAchat.entities.JobLog;
import com.example.gestionAchat.service.pm.JobLogService;
import com.example.gestionAchat.service.pm.dto.JobLogCriteria;
import com.example.gestionAchat.service.pm.query.JobLogQueryService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.example.gestionAchat.entities.JobLog}.
 */
@RestController
@RequestMapping("/api")
public class JobLogController {

    private static final String ENTITY_NAME = "jobLog";
    private final Logger log = LoggerFactory.getLogger(JobLogController.class);
    private final JobLogService jobLogService;
    private final JobLogQueryService jobLogQueryService;
    @Value("${clientApp.name}")
    private String applicationName;

    public JobLogController(JobLogService jobLogService, JobLogQueryService jobLogQueryService) {
        this.jobLogService = jobLogService;
        this.jobLogQueryService = jobLogQueryService;
    }

    /**
     * {@code POST  /job-logs} : Create a new jobLog.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to Create a new jobLog.
     * @param jobLog the jobLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobLog, or with status {@code 400 (Bad Request)} if the jobLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-logs")
    public ResponseEntity<JobLog> createJobLog(@RequestBody JobLog jobLog) throws URISyntaxException {
        log.debug("REST request to save JobLog : {}", jobLog);
        if (jobLog.getId() != null) {
            throw new BadRequestAlertException("A new jobLog cannot already have an ID", ENTITY_NAME, "idexists");
        }

        JobLog result = jobLogService.save(jobLog);
        return ResponseEntity.created(new URI("/api/job-logs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /job-logs} : Updates an existing jobLog.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to Update an existing jobLog.
     * @param jobLog the jobLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobLog,
     * or with status {@code 400 (Bad Request)} if the jobLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-logs")
    public ResponseEntity<JobLog> updateJobLog(@RequestBody JobLog jobLog) throws URISyntaxException {
        log.debug("REST request to update JobLog : {}", jobLog);
        if (jobLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobLog result = jobLogService.save(jobLog);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobLog.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /job-logs} : get all the jobLogs.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the jobLogs.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobLogs in body.
     */
    @GetMapping("/job-logs")
    public ResponseEntity<List<JobLog>> getAllJobLogs(JobLogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JobLogs by criteria: {}", criteria);
        Page<JobLog> page = jobLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-logs/count} : count all the jobLogs.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the jobLogs.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/job-logs/count")
    public ResponseEntity<Long> countJobLogs(JobLogCriteria criteria) {
        log.debug("REST request to count JobLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(jobLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /job-logs/{id}} : get jobLog By id.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get jobLog By id.
     * @param id the id of the jobLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-logs/{id}")
    public ResponseEntity<JobLog> getJobLog(@PathVariable Long id) {
        log.debug("REST request to get JobLog : {}", id);
        Optional<JobLog> jobLog = jobLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobLog);
    }

    /**
     * {@code DELETE  /job-logs/{id}} : delete jobLog By id.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete jobLog By id.
     * @param id the id of the jobLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-logs/{id}")
    public ResponseEntity<Void> deleteJobLog(@PathVariable Long id) {
        log.debug("REST request to delete JobLog : {}", id);
        jobLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
