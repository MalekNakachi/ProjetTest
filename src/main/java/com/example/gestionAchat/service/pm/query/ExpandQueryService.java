package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.Expand;
import com.example.gestionAchat.entities.Expand_;
import com.example.gestionAchat.repository.pm.ExpandRepository;
import com.example.gestionAchat.service.pm.dto.ExpandCriteria;

import java.util.List;


/**
 * Service for executing complex queries for {@link Expand} entities in the database.
 * The main input is a {@link ExpandCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Expand} or a {@link Page} of {@link Expand} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpandQueryService extends QueryService<Expand> {

    private final Logger log = LoggerFactory.getLogger(ExpandQueryService.class);

    private final ExpandRepository expandRepository;

    public ExpandQueryService(ExpandRepository expandRepository) {
        this.expandRepository = expandRepository;
    }

    /**
     * Return a {@link List} of {@link Expand} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Expand> findByCriteria(ExpandCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Expand> specification = createSpecification(criteria);
        return expandRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Expand} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Expand> findByCriteria(ExpandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Expand> specification = createSpecification(criteria);
        return expandRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpandCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Expand> specification = createSpecification(criteria);
        return expandRepository.count(specification);
    }

    /**
     * Function to convert {@link ExpandCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Expand> createSpecification(ExpandCriteria criteria) {
        Specification<Expand> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Expand_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), Expand_.userId));
            }
            if (criteria.getDepartement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartement(), Expand_.departement));
            }
            if (criteria.getGroupe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupe(), Expand_.groupe));
            }
        }
        return specification;
    }
}
