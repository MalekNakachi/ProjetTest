package com.example.gestionAchat.web.rest;

import com.example.gestionAchat.domain.Offre;
import com.example.gestionAchat.repository.OffreRepository;
import com.example.gestionAchat.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link import com.example.gestionAchat.domain.Offre}.
 */
@RestController
@RequestMapping("/api")
public class OffreResource {

    private final Logger log = LoggerFactory.getLogger(OffreResource.class);

    private static final String ENTITY_NAME = "offre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffreRepository offreRepository;

    @PersistenceContext(unitName = "externDSEmFactory")
    EntityManager em;
    public OffreResource(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    /**
     * {@code POST  /offres} : Create a new offre.
     *
     * @param offre the offre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offre, or with status {@code 400 (Bad Request)} if the offre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offres")
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre) throws URISyntaxException {
        log.debug("REST request to save Offre : {}", offre);
//        if (offre.getId() != null) {
//            throw new BadRequestAlertException("A new offre cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        Offre result = offreRepository.save(offre);
        return ResponseEntity.created(new URI("/api/offres/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }




    /**
     * {@code PUT  /offres} : Updates an existing offre.
     *
     * @param offre the offre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offre,
     * or with status {@code 400 (Bad Request)} if the offre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offres")
    public ResponseEntity<Offre> updateOffre(@RequestBody Offre offre) throws URISyntaxException {
        log.debug("REST request to update Offre : {}", offre);
        if (offre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Offre result = offreRepository.save(offre);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offre.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /offres} : get all the offres.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offres in body.
     */
    @GetMapping("/offres")
    public List<Offre> getAllOffres(@RequestParam(required = false) String filter) {
        if ("boncommande-is-null".equals(filter)) {
            log.debug("REST request to get all Offres where bonCommande is null");
            return StreamSupport
                    .stream(offreRepository.findAll().spliterator(), false)
                    .filter(offre -> offre.getBonCommande() == null)
                    .collect(Collectors.toList());
        }
        log.debug("REST request to get all Offres");
        return offreRepository.findAll();
    }


    @GetMapping("/getAllDemandeoffres")
    public List<Offre> getAllDemandeoffres(@RequestParam("demandeId") Long demandeId) {
        log.debug("REST request to get Article : {}", demandeId);


        String querySelect = "SELECT * FROM offre where offre.demandeAchat_id = "+demandeId+" ";

        Query q1 = em.createNativeQuery(querySelect, Offre.class);

        List<Offre> offres = q1.getResultList();
        return offres;
    }



    /**
     * {@code GET  /offres/:id} : get the "id" offre.
     *
     * @param id the id of the offre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offres/{id}")
    public ResponseEntity<Offre> getOffre(@PathVariable Long id) {
        log.debug("REST request to get Offre : {}", id);
        Optional<Offre> offre = offreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(offre);
    }


    @GetMapping("/offres/demande/{id}")
    public List<Offre> getDemandeOffre(@PathVariable Long id){

        log.debug("REST request to get Offre : {}", id);
        List<Offre> offre = offreRepository.findAllByDemandeAchat_Id(id);
        return offre;
    }


    @GetMapping("/offres/fournisseur/{id}")
    public List<Offre> getFournisseurOffre(@PathVariable Long id){

        log.debug("REST request to get Offre : {}", id);
        List<Offre> offre = offreRepository.findAllByFournisseur_Id(id);
        return offre;
    }


    @GetMapping("/offres/BonCommande/{id}")
    public List<Offre> getBonCommandeOffre(@PathVariable Long id){

        log.debug("REST request to get Offre : {}", id);
        List<Offre> offre = offreRepository.findAllByBonCommande_Id(id);
        return offre;
    }


    /**
     * {@code DELETE  /offres/:id} : delete the "id" offre.
     *
     * @param id the id of the offre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offres/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        log.debug("REST request to delete Offre : {}", id);
        offreRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
