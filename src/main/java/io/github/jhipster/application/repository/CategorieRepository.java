package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Categorie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Categorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
