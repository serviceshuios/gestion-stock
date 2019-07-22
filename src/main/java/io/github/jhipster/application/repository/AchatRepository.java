package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Achat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Achat entity.
 */
@Repository
public interface AchatRepository extends JpaRepository<Achat, Long> {

    @Query(value = "select distinct achat from Achat achat left join fetch achat.produits",
        countQuery = "select count(distinct achat) from Achat achat")
    Page<Achat> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct achat from Achat achat left join fetch achat.produits")
    List<Achat> findAllWithEagerRelationships();

    @Query("select achat from Achat achat left join fetch achat.produits where achat.id =:id")
    Optional<Achat> findOneWithEagerRelationships(@Param("id") Long id);

}
