package com.example.gestionAchat.web.rest;

import com.example.gestionAchat.domain.Reference;
import com.example.gestionAchat.domain.Reference;
import com.example.gestionAchat.repository.ReferenceRepository;
import com.example.gestionAchat.repository.ReferenceRepository;
import com.example.gestionAchat.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link Reference}.
 */
@RestController
@RequestMapping("/api/reference")
public class ReferenceResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceResource.class);

    private static final String ENTITY_NAME = "reference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferenceRepository referenceRepository;

    public ReferenceResource(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    /**
     * {@code POST  /bon-commandes} : Create a new bonCommande.
     *
     * @param reference the bonCommande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonCommande, or with status {@code 400 (Bad Request)} if the bonCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reference")
    public ResponseEntity<Reference> createReference(@RequestBody Reference reference) throws URISyntaxException {
        log.debug("REST request to save Reference : {}", reference);

        Reference result = referenceRepository.save(reference);
        return ResponseEntity.created(new URI("/api/reference/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-commandes} : Updates an existing bonCommande.
     *
     * @param reference the bonCommande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommande,
     * or with status {@code 400 (Bad Request)} if the bonCommande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonCommande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reference")
    public ResponseEntity<Reference> updateReference(@RequestBody Reference reference) throws URISyntaxException {
        log.debug("REST request to update Reference : {}", reference);
        if (reference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reference result = referenceRepository.save(reference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reference.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bon-commandes} : get all the bonCommandes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonCommandes in body.
     */
    @GetMapping("/reference")
    public List<Reference> getAllReference(@RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
//        if ("facture-is-null".equals(filter)) {
//            log.debug("REST request to get all Reference where facture is null");
//            return StreamSupport
//                .stream(referenceRepository.findAll().spliterator(), false)
//                .filter(reference -> reference.getFacture() == null)
//                .collect(Collectors.toList());
//        }
//        if ("processus-is-null".equals(filter)) {
//            log.debug("REST request to get all Reference where processus is null");
//            return StreamSupport
//                .stream(referenceRepository.findAll().spliterator(), false)
//                .filter(reference -> reference.getProcessus() == null)
//                .collect(Collectors.toList());
//        }
        log.debug("REST request to get all Reference");
        return referenceRepository.findAll();
    }

    /**
     * {@code GET  /reference/:id} : get the "id" reference.
     *
     * @param id the id of the reference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reference/{id}")
    public ResponseEntity<Reference> getReference(@PathVariable Long id) {
        log.debug("REST request to get Reference : {}", id);
        Optional<Reference> reference = referenceRepository.findById(id);
        reference.get().setCompteur( reference.get().getCompteur()+1);
         referenceRepository.save(reference.get());
        return ResponseUtil.wrapOrNotFound(reference);
    }


    /**
     * {@code DELETE  /bon-commandes/:id} : delete the "id" bonCommande.
     *
     * @param id the id of the bonCommande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reference/{id}")
    public ResponseEntity<Void> deleteReference(@PathVariable Long id) {
        log.debug("REST request to delete Reference : {}", id);
        referenceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
