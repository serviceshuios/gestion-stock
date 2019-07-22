package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "categorie_name", nullable = false)
    private String categorieName;

    @NotNull
    @Column(name = "categorie_description", nullable = false)
    private String categorieDescription;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorieName() {
        return categorieName;
    }

    public Categorie categorieName(String categorieName) {
        this.categorieName = categorieName;
        return this;
    }

    public void setCategorieName(String categorieName) {
        this.categorieName = categorieName;
    }

    public String getCategorieDescription() {
        return categorieDescription;
    }

    public Categorie categorieDescription(String categorieDescription) {
        this.categorieDescription = categorieDescription;
        return this;
    }

    public void setCategorieDescription(String categorieDescription) {
        this.categorieDescription = categorieDescription;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Categorie dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Categorie produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Categorie addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setOwner(this);
        return this;
    }

    public Categorie removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setOwner(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return id != null && id.equals(((Categorie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", categorieName='" + getCategorieName() + "'" +
            ", categorieDescription='" + getCategorieDescription() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            "}";
    }
}
