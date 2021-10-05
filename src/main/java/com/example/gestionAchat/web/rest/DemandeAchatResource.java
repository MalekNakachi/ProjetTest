package com.example.gestionAchat.web.rest;

import com.example.gestionAchat.domain.AclEntry;
import com.example.gestionAchat.domain.DemandeAchat;
import com.example.gestionAchat.repository.AclEntryRepository;
import com.example.gestionAchat.repository.DemandeAchatRepository;
import com.example.gestionAchat.web.rest.errors.BadRequestAlertException;
import com.groupdocs.conversion.internal.c.a.s.internal.mw.De;
import eu.bitwalker.useragentutils.UserAgent;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * REST controller for managing {@link com.example.gestionAchat.domain.DemandeAchat}.
 */
@RestController
@RequestMapping("/api")
public class DemandeAchatResource {


  @Autowired
  JdbcMutableAclService aclService;

    private final Logger log = LoggerFactory.getLogger(DemandeAchatResource.class);

    private static final String ENTITY_NAME = "demandeAchat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

  @Autowired
  AclEntryRepository aclEntryRepository;

  @PersistenceContext(unitName = "externDSEmFactory")
    EntityManager em;

    private final DemandeAchatRepository demandeAchatRepository;

    public DemandeAchatResource(DemandeAchatRepository demandeAchatRepository) {
        this.demandeAchatRepository = demandeAchatRepository;
    }

    /**
     * {@code POST  /demande-achats} : Create a new demandeAchat.
     *
     * @param demandeAchat the demandeAchat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeAchat, or with status {@code 400 (Bad Request)} if the demandeAchat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-achats")
    public ResponseEntity<DemandeAchat> createDemandeAchat(@RequestBody DemandeAchat demandeAchat) throws URISyntaxException {
        log.debug("REST request to save DemandeAchat : {}", demandeAchat);
//        if (demandeAchat.getId() != null) {
//            throw new BadRequestAlertException("A new demandeAchat cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        DemandeAchat result = demandeAchatRepository.save(demandeAchat);
        return ResponseEntity.created(new URI("/api/demande-achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


  @Transactional
    @GetMapping("/demande-achats-acl")
    public void createDemandeAchatAcl(@RequestParam("etat") String etat, @RequestParam("id") Long idDemande, @RequestParam("authors") List<String> authors,@RequestParam("readers") List<String> readers) throws URISyntaxException {
      log.debug("REST request to save DemandeAchat : {}", idDemande,authors,readers);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      System.out.println("authentification !!!!!!"+authentication);

      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      System.out.println("authorities::::::"+authorities);
      ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);


      if(etat.equals("Create")){


        ObjectIdentity oid =  new ObjectIdentityImpl(DemandeAchat.class, idDemande);
        System.out.println(oid.getIdentifier());
        MutableAcl acl = aclService.createAcl(oid);

        // anciens auteurs deviennent des lecteurs

        String querySelect = "SELECT * FROM acl_entry where acl_object_identity ='" + oid.getIdentifier() + "' and mask='2'";

        Query q1 = em.createNativeQuery(querySelect, AclEntry.class);

        List<AclEntry> aclEntries = q1.getResultList();

        System.out.println("aclEntries::::!!!!!"+aclEntries.size());
        for (int s = 0; s < aclEntries.size(); s++)

        {
          AclEntry aclEntry = aclEntries.get(s);
          aclEntry.setMask(1);
          aclEntryRepository.save(aclEntry);

        }

        if (authors.size() != 0) {

          for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals("")) {
              acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, new GrantedAuthoritySid(authors.get(i)), true);
            }

          }
          for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals("")) {

              acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid(authors.get(i)), true);
            }
          }
        }
        if (readers.size() != 0) {
          for (int i = 0; i < readers.size(); i++) {
            if (!readers.get(i).equals("")) {

              acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid(readers.get(i)), true);
            }
          }
        }

        aclService.updateAcl(acl);

      } else {

        ObjectIdentity oid =  new ObjectIdentityImpl(DemandeAchat.class, idDemande);
        System.out.println(oid.getIdentifier());
        MutableAcl acl = (MutableAcl) aclService.readAclById(oid);
        aclService.updateAcl(acl);

        // anciens auteurs deviennent des lecteurs

        String querySelect = "SELECT * FROM acl_entry where acl_object_identity ='" + oid.getIdentifier() + "' and mask='2'";

        Query q1 = em.createNativeQuery(querySelect, AclEntry.class);

        List<AclEntry> aclEntries = q1.getResultList();

        System.out.println("aclEntries::::!!!!!"+aclEntries.size());
        for (int s = 0; s < aclEntries.size(); s++)

        {
          AclEntry aclEntry = aclEntries.get(s);
          aclEntry.setMask(1);
          aclEntryRepository.save(aclEntry);

        }

        if (authors.size() != 0) {

          for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals("")) {
              acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, new GrantedAuthoritySid(authors.get(i)), true);
            }

          }
          for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals("")) {

              acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid(authors.get(i)), true);
            }
          }
        }
        if (readers.size() != 0) {
          for (int i = 0; i < readers.size(); i++) {
            if (!readers.get(i).equals("")) {

              acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid(readers.get(i)), true);
            }
          }
        }

        aclService.updateAcl(acl);

      }



    }
    /**
     * {@code PUT  /demande-achats} : Updates an existing demandeAchat.
     *
     * @param demandeAchat the demandeAchat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeAchat,
     * or with status {@code 400 (Bad Request)} if the demandeAchat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeAchat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-achats")
    public ResponseEntity<DemandeAchat> updateDemandeAchat(@RequestBody DemandeAchat demandeAchat) throws URISyntaxException {
        log.debug("REST request to update DemandeAchat : {}", demandeAchat);
        if (demandeAchat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DemandeAchat result = demandeAchatRepository.save(demandeAchat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeAchat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /demande-achats} : get all the demandeAchats.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeAchats in body.
     */
    @GetMapping("/demande-achats")
    public List<DemandeAchat> getAllDemandeAchats(@RequestParam(required = false) String filter) {
        if ("consultation-is-null".equals(filter)) {
            log.debug("REST request to get all DemandeAchats where consultation is null");
            return StreamSupport
                .stream(demandeAchatRepository.findAll().spliterator(), false)
                .filter(demandeAchat -> demandeAchat.getConsultation() == null)
                .collect(Collectors.toList());
        }
        if ("processus-is-null".equals(filter)) {
            log.debug("REST request to get all DemandeAchats where processus is null");
            return StreamSupport
                .stream(demandeAchatRepository.findAll().spliterator(), false)
                .filter(demandeAchat -> demandeAchat.getProcessus() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all DemandeAchats");
        return demandeAchatRepository.findAll();
    }



  @GetMapping("/demande-achats-check-acl")
  public List<DemandeAchat> getAllDemandeAchats(@RequestParam("profil") String profil,@RequestParam("username") String username,@RequestParam("profileUser") String profileUser) {
    String querySelect = " SELECT * FROM demande_achat WHERE id IN (SELECT acl_object_identity.object_id_identity FROM acl_object_identity  WHERE id IN ((SELECT acl_entry.acl_object_identity FROM acl_entry WHERE sid IN " +
      "(SELECT id FROM acl_sid WHERE sid IN ('" + username + "','" + profil + "','" + profileUser + "')))))";


    Query q1 = em.createNativeQuery(querySelect, DemandeAchat.class);

    List<DemandeAchat> courriers = q1.getResultList();

    return courriers;
  }


    /**
     * {@code GET  /demande-achats/:id} : get the "id" demandeAchat.
     *
     * @param id the id of the demandeAchat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeAchat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-achats/{id}")
    public ResponseEntity<DemandeAchat> getDemandeAchat(@PathVariable Long id) {
        log.debug("REST request to get DemandeAchat : {}", id);
        Optional<DemandeAchat> demandeAchat = demandeAchatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(demandeAchat);
    }

    /**
     * {@code DELETE  /demande-achats/:id} : delete the "id" demandeAchat.
     *
     * @param id the id of the demandeAchat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-achats/{id}")
    public ResponseEntity<Void> deleteDemandeAchat(@PathVariable Long id) {
        log.debug("REST request to delete DemandeAchat : {}", id);
        demandeAchatRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


}
