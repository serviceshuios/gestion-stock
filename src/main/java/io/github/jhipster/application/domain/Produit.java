package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "produit_nom", nullable = false)
    private String produitNom;

    @NotNull
    @Column(name = "produit_description", nullable = false)
    private String produitDescription;

    @NotNull
    @Column(name = "produit_prix", nullable = false)
    private Double produitPrix;

    @NotNull
    @Column(name = "produit_stock", nullable = false)
    private Integer produitStock;

    @ManyToOne
    @JsonIgnoreProperties("produits")
    private Categorie owner;

    @ManyToMany(mappedBy = "produits")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Achat> owners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduitNom() {
        return produitNom;
    }

    public Produit produitNom(String produitNom) {
        this.produitNom = produitNom;
        return this;
    }

    public void setProduitNom(String produitNom) {
        this.produitNom = produitNom;
    }

    public String getProduitDescription() {
        return produitDescription;
    }

    public Produit produitDescription(String produitDescription) {
        this.produitDescription = produitDescription;
        return this;
    }

    public void setProduitDescription(String produitDescription) {
        this.produitDescription = produitDescription;
    }

    public Double getProduitPrix() {
        return produitPrix;
    }

    public Produit produitPrix(Double produitPrix) {
        this.produitPrix = produitPrix;
        return this;
    }

    public void setProduitPrix(Double produitPrix) {
        this.produitPrix = produitPrix;
    }

    public Integer getProduitStock() {
        return produitStock;
    }

    public Produit produitStock(Integer produitStock) {
        this.produitStock = produitStock;
        return this;
    }

    public void setProduitStock(Integer produitStock) {
        this.produitStock = produitStock;
    }

    public Categorie getOwner() {
        return owner;
    }

    public Produit owner(Categorie categorie) {
        this.owner = categorie;
        return this;
    }

    public void setOwner(Categorie categorie) {
        this.owner = categorie;
    }

    public Set<Achat> getOwners() {
        return owners;
    }

    public Produit owners(Set<Achat> achats) {
        this.owners = achats;
        return this;
    }

    public Produit addOwner(Achat achat) {
        this.owners.add(achat);
        achat.getProduits().add(this);
        return this;
    }

    public Produit removeOwner(Achat achat) {
        this.owners.remove(achat);
        achat.getProduits().remove(this);
        return this;
    }

    public void setOwners(Set<Achat> achats) {
        this.owners = achats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", produitNom='" + getProduitNom() + "'" +
            ", produitDescription='" + getProduitDescription() + "'" +
            ", produitPrix=" + getProduitPrix() +
            ", produitStock=" + getProduitStock() +
            "}";
    }
}
