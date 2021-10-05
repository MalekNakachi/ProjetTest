package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.Variable;
import com.example.gestionAchat.entities.Variable_;
import com.example.gestionAchat.repository.pm.VarRepository;
import com.example.gestionAchat.service.pm.dto.VariableCriteria;

import javax.persistence.criteria.JoinType;
import java.util.List;

import com.example.gestionAchat.entities.Application_;
/**
 * Service for executing complex queries for {@link Variable} entities in the database.
 * The main input is a {@link VariableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Variable} or a {@link Page} of {@link Variable} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VariableQueryService extends QueryService<Variable> {

    private final Logger log = LoggerFactory.getLogger(VariableQueryService.class);

    private final VarRepository variableRepository;

    public VariableQueryService(VarRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    /**
     * Return a {@link List} of {@link Variable} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Variable> findByCriteria(VariableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Variable> specification = createSpecification(criteria);
        return variableRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Variable} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Variable> findByCriteria(VariableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Variable> specification = createSpecification(criteria);
        return variableRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VariableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Variable> specification = createSpecification(criteria);
        return variableRepository.count(specification);
    }

    /**
     * Function to convert {@link VariableCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Variable> createSpecification(VariableCriteria criteria) {
        Specification<Variable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Variable_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Variable_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Variable_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Variable_.description));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Variable_.comment));
            }
            if (criteria.isStatus() != null) {
                specification = specification.and(buildSpecification(criteria.isStatus(), Variable_.status));
            }
            if (criteria.isTest() != null) {
                specification = specification.and(buildSpecification(criteria.isTest(), Variable_.test));
            }
            if (criteria.getDateupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateupdate(), Variable_.dateupdate));
            }
            if (criteria.getDatecreate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatecreate(), Variable_.datecreate));
            }
            if (criteria.getReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReader(), Variable_.reader));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), Variable_.author));
            }
            if (criteria.getCreatedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedby(), Variable_.createdby));
            }
            if (criteria.getModifiedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedby(), Variable_.modifiedby));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                        root -> root.join(Variable_.application, JoinType.LEFT).get(Application_.id)));
            }
        }
        return specification;
    }
}
