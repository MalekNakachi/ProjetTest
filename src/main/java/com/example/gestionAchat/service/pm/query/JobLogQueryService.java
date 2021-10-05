package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.JobLog;
import com.example.gestionAchat.entities.JobLog_;
import com.example.gestionAchat.repository.pm.JobLogRepository;
import com.example.gestionAchat.service.pm.dto.JobLogCriteria;

import java.util.List;

/**
 * Service for executing complex queries for {@link JobLog} entities in the database.
 * The main input is a {@link JobLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobLog} or a {@link Page} of {@link JobLog} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobLogQueryService extends QueryService<JobLog> {

    private final Logger log = LoggerFactory.getLogger(JobLogQueryService.class);

    private final JobLogRepository jobLogRepository;

    public JobLogQueryService(JobLogRepository jobLogRepository) {
        this.jobLogRepository = jobLogRepository;
    }

    /**
     * Return a {@link List} of {@link JobLog} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobLog> findByCriteria(JobLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobLog> specification = createSpecification(criteria);
        return jobLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link JobLog} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobLog> findByCriteria(JobLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobLog> specification = createSpecification(criteria);
        return jobLogRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobLog> specification = createSpecification(criteria);
        return jobLogRepository.count(specification);
    }

    /**
     * Function to convert {@link JobLogCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JobLog> createSpecification(JobLogCriteria criteria) {
        Specification<JobLog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JobLog_.id));
            }
            if (criteria.getPid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPid(), JobLog_.pid));
            }
            if (criteria.getJob() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJob(), JobLog_.job));
            }
            if (criteria.getLog() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLog(), JobLog_.log));
            }
            if (criteria.getFatherPid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherPid(), JobLog_.fatherPid));
            }
            if (criteria.getRootPid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRootPid(), JobLog_.rootPid));
            }
            if (criteria.getSystemPid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSystemPid(), JobLog_.systemPid));
            }
            if (criteria.getJobRepositoryId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobRepositoryId(), JobLog_.jobRepositoryId));
            }
            if (criteria.getJobVersion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobVersion(), JobLog_.jobVersion));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), JobLog_.context));
            }
            if (criteria.getOrigin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrigin(), JobLog_.origin));
            }
            if (criteria.getMessageType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessageType(), JobLog_.messageType));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), JobLog_.message));
            }
            if (criteria.getProject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProject(), JobLog_.project));
            }
            if (criteria.getMoment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoment(), JobLog_.moment));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), JobLog_.duration));
            }
            if (criteria.getObjectClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObjectClass(), JobLog_.objectClass));
            }
            if (criteria.getObjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getObjectId(), JobLog_.objectId));
            }
            if (criteria.getParam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParam(), JobLog_.param));
            }
        }
        return specification;
    }
}
