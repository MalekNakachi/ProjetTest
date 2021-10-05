package com.example.gestionAchat.web.rest;

import com.example.gestionAchat.domain.Pv;
import com.example.gestionAchat.repository.PvRepository;
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
 * REST controller for managing {@link com.example.gestionAchat.domain.Pv}.
 */
@RestController
@RequestMapping("/api")
public class PvResource {

    private final Logger log = LoggerFactory.getLogger(PvResource.class);

    private static final String ENTITY_NAME = "pv";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PvRepository pvRepository;

    public PvResource(PvRepository pvRepository) {
        this.pvRepository = pvRepository;
    }

    /**
     * {@code POST  /pvs} : Create a new pv.
     *
     * @param pv the pv to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pv, or with status {@code 400 (Bad Request)} if the pv has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pvs")
    public ResponseEntity<Pv> createPv(@RequestBody Pv pv) throws URISyntaxException {
        log.debug("REST request to save Pv : {}", pv);
//        if (pv.getId() != null) {
//            throw new BadRequestAlertException("A new pv cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        Pv result = pvRepository.save(pv);
        return ResponseEntity.created(new URI("/api/pvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pvs} : Updates an existing pv.
     *
     * @param pv the pv to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pv,
     * or with status {@code 400 (Bad Request)} if the pv is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pv couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pvs")
    public ResponseEntity<Pv> updatePv(@RequestBody Pv pv) throws URISyntaxException {
        log.debug("REST request to update Pv : {}", pv);
        if (pv.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pv result = pvRepository.save(pv);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pv.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pvs} : get all the pvs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pvs in body.
     */
    @GetMapping("/pvs")
    public List<Pv> getAllPvs(@RequestParam(required = false) String filter) {
        if ("facture-is-null".equals(filter)) {
            log.debug("REST request to get all Pvs where facture is null");
            return StreamSupport
                .stream(pvRepository.findAll().spliterator(), false)
                .filter(pv -> pv.getFacture() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Pvs");
        return pvRepository.findAll();
    }

    /**
     * {@code GET  /pvs/:id} : get the "id" pv.
     *
     * @param id the id of the pv to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pv, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pvs/{id}")
    public ResponseEntity<Pv> getPv(@PathVariable Long id) {
        log.debug("REST request to get Pv : {}", id);
        Optional<Pv> pv = pvRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pv);
    }

    /**
     * {@code DELETE  /pvs/:id} : delete the "id" pv.
     *
     * @param id the id of the pv to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pvs/{id}")
    public ResponseEntity<Void> deletePv(@PathVariable Long id) {
        log.debug("REST request to delete Pv : {}", id);
        pvRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
