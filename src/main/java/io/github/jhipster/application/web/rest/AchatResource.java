package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Achat;
import io.github.jhipster.application.repository.AchatRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Achat}.
 */
@RestController
@RequestMapping("/api")
public class AchatResource {

    private final Logger log = LoggerFactory.getLogger(AchatResource.class);

    private static final String ENTITY_NAME = "achat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AchatRepository achatRepository;

    public AchatResource(AchatRepository achatRepository) {
        this.achatRepository = achatRepository;
    }

    /**
     * {@code POST  /achats} : Create a new achat.
     *
     * @param achat the achat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new achat, or with status {@code 400 (Bad Request)} if the achat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/achats")
    public ResponseEntity<Achat> createAchat(@Valid @RequestBody Achat achat) throws URISyntaxException {
        log.debug("REST request to save Achat : {}", achat);
        if (achat.getId() != null) {
            throw new BadRequestAlertException("A new achat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Achat result = achatRepository.save(achat);
        return ResponseEntity.created(new URI("/api/achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /achats} : Updates an existing achat.
     *
     * @param achat the achat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated achat,
     * or with status {@code 400 (Bad Request)} if the achat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the achat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/achats")
    public ResponseEntity<Achat> updateAchat(@Valid @RequestBody Achat achat) throws URISyntaxException {
        log.debug("REST request to update Achat : {}", achat);
        if (achat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Achat result = achatRepository.save(achat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, achat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /achats} : get all the achats.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of achats in body.
     */
    @GetMapping("/achats")
    public List<Achat> getAllAchats(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Achats");
        return achatRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /achats/:id} : get the "id" achat.
     *
     * @param id the id of the achat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the achat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/achats/{id}")
    public ResponseEntity<Achat> getAchat(@PathVariable Long id) {
        log.debug("REST request to get Achat : {}", id);
        Optional<Achat> achat = achatRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(achat);
    }

    /**
     * {@code DELETE  /achats/:id} : delete the "id" achat.
     *
     * @param id the id of the achat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/achats/{id}")
    public ResponseEntity<Void> deleteAchat(@PathVariable Long id) {
        log.debug("REST request to delete Achat : {}", id);
        achatRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
