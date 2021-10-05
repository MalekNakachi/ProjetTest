package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.*;
import com.example.gestionAchat.repository.pm.UserAccessRepository;
import com.example.gestionAchat.service.pm.dto.UserAccessCriteria;

import javax.persistence.criteria.JoinType;
import java.util.List;

import com.example.gestionAchat.entities.Application_;

/**
 * Service for executing complex queries for {@link UserAccess} entities in the database.
 * The main input is a {@link UserAccessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAccess} or a {@link Page} of {@link UserAccess} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAccessQueryService extends QueryService<UserAccess> {

    private final Logger log = LoggerFactory.getLogger(UserAccessQueryService.class);

    private final UserAccessRepository userAccessRepository;

    public UserAccessQueryService(UserAccessRepository userAccessRepository) {
        this.userAccessRepository = userAccessRepository;
    }

    /**
     * Return a {@link List} of {@link UserAccess} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAccess> findByCriteria(UserAccessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserAccess> specification = createSpecification(criteria);
        return userAccessRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserAccess} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAccess> findByCriteria(UserAccessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserAccess> specification = createSpecification(criteria);
        return userAccessRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAccessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserAccess> specification = createSpecification(criteria);
        return userAccessRepository.count(specification);
    }

    /**
     * Function to convert {@link UserAccessCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserAccess> createSpecification(UserAccessCriteria criteria) {
        Specification<UserAccess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserAccess_.id));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), UserAccess_.comment));
            }
            if (criteria.isStatus() != null) {
                specification = specification.and(buildSpecification(criteria.isStatus(), UserAccess_.status));
            }
            if (criteria.isTest() != null) {
                specification = specification.and(buildSpecification(criteria.isTest(), UserAccess_.test));
            }
            if (criteria.getDateupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateupdate(), UserAccess_.dateupdate));
            }
            if (criteria.getDatecreate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatecreate(), UserAccess_.datecreate));
            }
            if (criteria.getReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReader(), UserAccess_.reader));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), UserAccess_.author));
            }
            if (criteria.getCreatedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedby(), UserAccess_.createdby));
            }
            if (criteria.getModifiedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedby(), UserAccess_.modifiedby));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                        root -> root.join(UserAccess_.application, JoinType.LEFT).get(Application_.id)));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                        root -> root.join(UserAccess_.profile, JoinType.LEFT).get(Profile_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                        root -> root.join(UserAccess_.user, JoinType.LEFT).get(User_.id)));
            }

            if (criteria.getProfileProfilename() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileProfilename(),
                        root -> root.join(UserAccess_.profile, JoinType.LEFT).get(Profile_.profilename)));
            }
            if (criteria.getApplicationAppname() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationAppname(),
                        root -> root.join(UserAccess_.application, JoinType.LEFT).get(Application_.alias)));
            }
            if (criteria.getUserSamaccountname() != null) {
                specification = specification.and(buildSpecification(criteria.getUserSamaccountname(),
                        root -> root.join(UserAccess_.user, JoinType.LEFT).get(User_.samaccountname)));
            }
        }
        return specification;
    }
}
