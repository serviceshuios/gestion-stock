package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Produit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
