package com.example.gestionAchat.service.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.entities.Application;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Application}.
 */
@Service
@Transactional
public class ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    AppRepository appRepository;

    public ApplicationService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    /**
     * Save a jobLog.
     *
     * @param jobLog the entity to save.
     * @return the persisted entity.
     */
    public Application save(Application jobLog) {
        log.debug("Request to save Application : {}", jobLog);
        return appRepository.save(jobLog);
    }

    /**
     * Get all the jobLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Application> findAll(Pageable pageable) {
        log.debug("Request to get all Applications");
        return appRepository.findAll(pageable);
    }


    /**
     * Get one jobLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Application> findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        return appRepository.findById(id);
    }

    /**
     * Delete the jobLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        appRepository.deleteById(id);
    }


    public Application autosave(String alias) {

        Optional<Application> r = appRepository.findByAlias(alias);
        if (!r.isPresent()) {
            Application p = new Application();
            p.setAlias(alias);
            p.setStatus(true);
            p.setTest(false);
            p.setAuthor("AppWriter,SUPLOCALADMIN");
            p.setReader("AppReader");
            p.setCreatedby("System");
            p.setDatecreate(LocalDate.now());
            p.setComment("auto create");
            return appRepository.save(p);
        }
        return r.get();
    }
}
