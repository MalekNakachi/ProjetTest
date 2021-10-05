package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.AuthAffectation;
import com.example.gestionAchat.entities.AuthAffectation_;
import com.example.gestionAchat.entities.Profile_;
import com.example.gestionAchat.repository.pm.AuthAffectationRepository;
import com.example.gestionAchat.service.pm.dto.AuthAffectationCriteria;
import com.example.gestionAchat.entities.Application_;
import javax.persistence.criteria.JoinType;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class AuthAffectationQueryService extends QueryService<AuthAffectation> {

    private final Logger log = LoggerFactory.getLogger(AuthAffectationQueryService.class);

    private final AuthAffectationRepository authAffectationRepository;

    public AuthAffectationQueryService(AuthAffectationRepository authAffectationRepository) {
        this.authAffectationRepository = authAffectationRepository;
    }

    /**
     * Return a {@link List} of {@link AuthAffectation} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthAffectation> findByCriteria(AuthAffectationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AuthAffectation> specification = createSpecification(criteria);
        return authAffectationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AuthAffectation} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthAffectation> findByCriteria(AuthAffectationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AuthAffectation> specification = createSpecification(criteria);
        return authAffectationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuthAffectationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AuthAffectation> specification = createSpecification(criteria);
        return authAffectationRepository.count(specification);
    }

    /**
     * Function to convert {@link AuthAffectationCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AuthAffectation> createSpecification(AuthAffectationCriteria criteria) {
        Specification<AuthAffectation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AuthAffectation_.id));
            }
            if (criteria.getGroupe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupe(), AuthAffectation_.groupe));
            }
            if (criteria.getOrders() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrders(), AuthAffectation_.orders));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), AuthAffectation_.comment));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), AuthAffectation_.status));
            }
            if (criteria.getTest() != null) {
                specification = specification.and(buildSpecification(criteria.getTest(), AuthAffectation_.test));
            }
            if (criteria.getDateupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateupdate(), AuthAffectation_.dateupdate));
            }
            if (criteria.getDatecreate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatecreate(), AuthAffectation_.datecreate));
            }
            if (criteria.getReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReader(), AuthAffectation_.reader));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), AuthAffectation_.author));
            }
            if (criteria.getCreatedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedby(), AuthAffectation_.createdby));
            }
            if (criteria.getModifiedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedby(), AuthAffectation_.modifiedby));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                        root -> root.join(AuthAffectation_.application, JoinType.LEFT).get(Application_.id)));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                        root -> root.join(AuthAffectation_.profile, JoinType.LEFT).get(Profile_.id)));
            }

            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                        root -> root.join(AuthAffectation_.application, JoinType.LEFT).get(Application_.id)));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                        root -> root.join(AuthAffectation_.profile, JoinType.LEFT).get(Profile_.id)));
            }

            if (criteria.getApplicationAppname() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationAppname(),
                        root -> root.join(AuthAffectation_.application, JoinType.LEFT).get(Application_.alias)));
            }
            if (criteria.getProfileProfilename() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileProfilename(),
                        root -> root.join(AuthAffectation_.profile, JoinType.LEFT).get(Profile_.profilename)));
            }
        }
        return specification;
    }
}
