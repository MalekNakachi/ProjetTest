package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.service.pm.dto.ApplicationCriteria;
import com.example.gestionAchat.entities.Application;
import com.example.gestionAchat.entities.Application_;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class ApplicationQueryService extends QueryService<Application> {

    private final Logger log = LoggerFactory.getLogger(ApplicationQueryService.class);

    private final AppRepository applicationRepository;

    public ApplicationQueryService(AppRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * Return a {@link List} of {@link Application} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Application> findByCriteria(ApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Application} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Application> findByCriteria(ApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Application> createSpecification(ApplicationCriteria criteria) {
        Specification<Application> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Application_.id));
            }
            if (criteria.getAlias() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlias(), Application_.alias));
            }
            if (criteria.getAppname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppname(), Application_.appname));
            }
            if (criteria.getAppdescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppdescription(), Application_.appdescription));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Application_.comment));
            }
            if (criteria.isStatus() != null) {
                specification = specification.and(buildSpecification(criteria.isStatus(), Application_.status));
            }
            if (criteria.isTest() != null) {
                specification = specification.and(buildSpecification(criteria.isTest(), Application_.test));
            }
            if (criteria.getDateupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateupdate(), Application_.dateupdate));
            }
            if (criteria.getDatecreate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatecreate(), Application_.datecreate));
            }
            if (criteria.getReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReader(), Application_.reader));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), Application_.author));
            }
            if (criteria.getCreatedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedby(), Application_.createdby));
            }
            if (criteria.getModifiedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedby(), Application_.modifiedby));
            }
        }
        return specification;
    }
}
