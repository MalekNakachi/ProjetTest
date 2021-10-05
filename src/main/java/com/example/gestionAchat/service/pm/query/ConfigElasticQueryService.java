package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.ConfigElastic;
import com.example.gestionAchat.entities.ConfigElastic_;
import com.example.gestionAchat.repository.pm.ElasticConfigRepository;
import com.example.gestionAchat.service.pm.dto.ConfigElasticCriteria;
import com.example.gestionAchat.entities.Application_;
import javax.persistence.criteria.JoinType;
import java.util.List;


/**
 * Service for executing complex queries for {@link ConfigElastic} entities in the database.
 * The main input is a {@link ConfigElasticCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConfigElastic} or a {@link Page} of {@link ConfigElastic} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConfigElasticQueryService extends QueryService<ConfigElastic> {

    private final Logger log = LoggerFactory.getLogger(ConfigElasticQueryService.class);

    private final ElasticConfigRepository configElasticRepository;

    public ConfigElasticQueryService(ElasticConfigRepository configElasticRepository) {
        this.configElasticRepository = configElasticRepository;
    }

    /**
     * Return a {@link List} of {@link ConfigElastic} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConfigElastic> findByCriteria(ConfigElasticCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConfigElastic> specification = createSpecification(criteria);
        return configElasticRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConfigElastic} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigElastic> findByCriteria(ConfigElasticCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConfigElastic> specification = createSpecification(criteria);
        return configElasticRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfigElasticCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConfigElastic> specification = createSpecification(criteria);
        return configElasticRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfigElasticCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConfigElastic> createSpecification(ConfigElasticCriteria criteria) {
        Specification<ConfigElastic> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConfigElastic_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ConfigElastic_.name));
            }
            if (criteria.getHosturl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHosturl(), ConfigElastic_.hosturl));
            }
            if (criteria.getPort() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPort(), ConfigElastic_.port));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), ConfigElastic_.comment));
            }
            if (criteria.isStatus() != null) {
                specification = specification.and(buildSpecification(criteria.isStatus(), ConfigElastic_.status));
            }
            if (criteria.isTest() != null) {
                specification = specification.and(buildSpecification(criteria.isTest(), ConfigElastic_.test));
            }
            if (criteria.getDateupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateupdate(), ConfigElastic_.dateupdate));
            }
            if (criteria.getDatecreate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatecreate(), ConfigElastic_.datecreate));
            }
            if (criteria.getReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReader(), ConfigElastic_.reader));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), ConfigElastic_.author));
            }
            if (criteria.getCreatedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedby(), ConfigElastic_.createdby));
            }
            if (criteria.getModifiedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedby(), ConfigElastic_.modifiedby));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                        root -> root.join(ConfigElastic_.application, JoinType.LEFT).get(Application_.id)));
            }
        }
        return specification;
    }
}
