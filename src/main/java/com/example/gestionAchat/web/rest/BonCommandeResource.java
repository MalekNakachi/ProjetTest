package com.example.gestionAchat.web.rest;

import com.example.gestionAchat.domain.BonCommande;
import com.example.gestionAchat.repository.BonCommandeRepository;
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
 * REST controller for managing {@link com.example.gestionAchat.domain.BonCommande}.
 */
@RestController
@RequestMapping("/api")
public class BonCommandeResource {

    private final Logger log = LoggerFactory.getLogger(BonCommandeResource.class);

    private static final String ENTITY_NAME = "bonCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonCommandeRepository bonCommandeRepository;

    public BonCommandeResource(BonCommandeRepository bonCommandeRepository) {
        this.bonCommandeRepository = bonCommandeRepository;
    }

    /**
     * {@code POST  /bon-commandes} : Create a new bonCommande.
     *
     * @param bonCommande the bonCommande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonCommande, or with status {@code 400 (Bad Request)} if the bonCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bon-commandes")
    public ResponseEntity<BonCommande> createBonCommande(@RequestBody BonCommande bonCommande) throws URISyntaxException {
        log.debug("REST request to save BonCommande : {}", bonCommande);

        BonCommande result = bonCommandeRepository.save(bonCommande);
        return ResponseEntity.created(new URI("/api/bon-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-commandes} : Updates an existing bonCommande.
     *
     * @param bonCommande the bonCommande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommande,
     * or with status {@code 400 (Bad Request)} if the bonCommande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonCommande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bon-commandes")
    public ResponseEntity<BonCommande> updateBonCommande(@RequestBody BonCommande bonCommande) throws URISyntaxException {
        log.debug("REST request to update BonCommande : {}", bonCommande);
        if (bonCommande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BonCommande result = bonCommandeRepository.save(bonCommande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommande.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bon-commandes} : get all the bonCommandes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonCommandes in body.
     */
    @GetMapping("/bon-commandes")
    public List<BonCommande> getAllBonCommandes(@RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("facture-is-null".equals(filter)) {
            log.debug("REST request to get all BonCommandes where facture is null");
            return StreamSupport
                .stream(bonCommandeRepository.findAll().spliterator(), false)
                .filter(bonCommande -> bonCommande.getFacture() == null)
                .collect(Collectors.toList());
        }
        if ("processus-is-null".equals(filter)) {
            log.debug("REST request to get all BonCommandes where processus is null");
            return StreamSupport
                .stream(bonCommandeRepository.findAll().spliterator(), false)
                .filter(bonCommande -> bonCommande.getProcessus() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all BonCommandes");
        return bonCommandeRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /bon-commandes/:id} : get the "id" bonCommande.
     *
     * @param id the id of the bonCommande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonCommande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bon-commandes/{id}")
    public ResponseEntity<BonCommande> getBonCommande(@PathVariable Long id) {
        log.debug("REST request to get BonCommande : {}", id);
        Optional<BonCommande> bonCommande = bonCommandeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(bonCommande);
    }

    /**
     * {@code DELETE  /bon-commandes/:id} : delete the "id" bonCommande.
     *
     * @param id the id of the bonCommande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bon-commandes/{id}")
    public ResponseEntity<Void> deleteBonCommande(@PathVariable Long id) {
        log.debug("REST request to delete BonCommande : {}", id);
        bonCommandeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
