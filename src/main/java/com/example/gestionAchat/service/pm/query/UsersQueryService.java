package com.example.gestionAchat.service.pm.query;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.ConfigLdap_;
import com.example.gestionAchat.entities.User;
import com.example.gestionAchat.entities.User_;
import com.example.gestionAchat.repository.pm.UserRepository;
import com.example.gestionAchat.service.pm.dto.UsersCriteria;

import javax.persistence.criteria.JoinType;
import java.util.List;


/**
 * Service for executing complex queries for {@link User} entities in the database.
 * The main input is a {@link UsersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link User} or a {@link Page} of {@link User} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsersQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UsersQueryService.class);

    private final UserRepository usersRepository;

    public UsersQueryService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Return a {@link List} of {@link User} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<User> findByCriteria(UsersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return usersRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link User} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<User> findByCriteria(UsersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<User> specification = createSpecification(criteria);
        return usersRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return usersRepository.count(specification);
    }

    /**
     * Function to convert {@link UsersCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<User> createSpecification(UsersCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getSamaccountname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSamaccountname(), User_.samaccountname));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), User_.mail));
            }
            if (criteria.getManager() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManager(), User_.manager));
            }
            if (criteria.getDisplayname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayname(), User_.displayname));
            }
            if (criteria.getDistinguishedname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistinguishedname(), User_.distinguishedname));
            }
            if (criteria.getThumbnailphoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnailphoto(), User_.thumbnailphoto));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), User_.description));
            }
            if (criteria.getMemberof() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberof(), User_.memberof));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), User_.password));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), User_.comment));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), User_.status));
            }
            if (criteria.getTest() != null) {
                specification = specification.and(buildSpecification(criteria.getTest(), User_.test));
            }
            if (criteria.getDateupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateupdate(), User_.dateupdate));
            }
            if (criteria.getDatecreate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatecreate(), User_.datecreate));
            }
            if (criteria.getReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReader(), User_.reader));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), User_.author));
            }
            if (criteria.getCreatedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedby(), User_.createdby));
            }
            if (criteria.getModifiedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedby(), User_.modifiedby));
            }
            if (criteria.getEmployeetype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeetype(), User_.employeetype));
            }
            if (criteria.getConfigLdapId() != null) {
                specification = specification.and(buildSpecification(criteria.getConfigLdapId(),
                        root -> root.join(User_.configLdap, JoinType.LEFT).get(ConfigLdap_.id)));
            }
        }
        return specification;
    }
}
