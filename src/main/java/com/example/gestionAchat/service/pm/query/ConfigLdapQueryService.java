package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.ConfigLdap;
import com.example.gestionAchat.entities.ConfigLdap_;
import com.example.gestionAchat.repository.pm.LdapConfigRepository;
import com.example.gestionAchat.service.pm.dto.ConfigLdapCriteria;



import java.util.List;



@Service
@Transactional(readOnly = true)
public class ConfigLdapQueryService extends QueryService<ConfigLdap> {

    private final Logger log = LoggerFactory.getLogger(ConfigLdapQueryService.class);

    private final LdapConfigRepository configLdapRepository;

    public ConfigLdapQueryService(LdapConfigRepository configLdapRepository) {
        this.configLdapRepository = configLdapRepository;
    }

    /**
     * Return a {@link List} of {@link ConfigLdap} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConfigLdap> findByCriteria(ConfigLdapCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConfigLdap> specification = createSpecification(criteria);
        return configLdapRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConfigLdap} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigLdap> findByCriteria(ConfigLdapCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConfigLdap> specification = createSpecification(criteria);
        return configLdapRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfigLdapCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConfigLdap> specification = createSpecification(criteria);
        return configLdapRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfigLdapCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConfigLdap> createSpecification(ConfigLdapCriteria criteria) {
        Specification<ConfigLdap> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConfigLdap_.id));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), ConfigLdap_.url));
            }
            if (criteria.getBase() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBase(), ConfigLdap_.base));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), ConfigLdap_.password));
            }
            if (criteria.getUserDn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserDn(), ConfigLdap_.userDn));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ConfigLdap_.name));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), ConfigLdap_.comment));
            }
            if (criteria.isStatus() != null) {
                specification = specification.and(buildSpecification(criteria.isStatus(), ConfigLdap_.status));
            }
            if (criteria.isTest() != null) {
                specification = specification.and(buildSpecification(criteria.isTest(), ConfigLdap_.test));
            }
            if (criteria.getDateupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateupdate(), ConfigLdap_.dateupdate));
            }
            if (criteria.getDatecreate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatecreate(), ConfigLdap_.datecreate));
            }
            if (criteria.getReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReader(), ConfigLdap_.reader));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), ConfigLdap_.author));
            }
            if (criteria.getCreatedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedby(), ConfigLdap_.createdby));
            }
            if (criteria.getModifiedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedby(), ConfigLdap_.modifiedby));
            }
        }
        return specification;
    }
}
