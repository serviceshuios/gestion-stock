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
 * A Achat.
 */
@Entity
@Table(name = "achat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Achat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "achat_date", nullable = false)
    private LocalDate achatDate;

    @NotNull
    @Column(name = "achat_description", nullable = false)
    private String achatDescription;

    @NotNull
    @Column(name = "achat_quantite", nullable = false)
    private Integer achatQuantite;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "achat_produit",
               joinColumns = @JoinColumn(name = "achat_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"))
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAchatDate() {
        return achatDate;
    }

    public Achat achatDate(LocalDate achatDate) {
        this.achatDate = achatDate;
        return this;
    }

    public void setAchatDate(LocalDate achatDate) {
        this.achatDate = achatDate;
    }

    public String getAchatDescription() {
        return achatDescription;
    }

    public Achat achatDescription(String achatDescription) {
        this.achatDescription = achatDescription;
        return this;
    }

    public void setAchatDescription(String achatDescription) {
        this.achatDescription = achatDescription;
    }

    public Integer getAchatQuantite() {
        return achatQuantite;
    }

    public Achat achatQuantite(Integer achatQuantite) {
        this.achatQuantite = achatQuantite;
        return this;
    }

    public void setAchatQuantite(Integer achatQuantite) {
        this.achatQuantite = achatQuantite;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Achat produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Achat addProduit(Produit produit) {
        this.produits.add(produit);
        produit.getOwners().add(this);
        return this;
    }

    public Achat removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.getOwners().remove(this);
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
        if (!(o instanceof Achat)) {
            return false;
        }
        return id != null && id.equals(((Achat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Achat{" +
            "id=" + getId() +
            ", achatDate='" + getAchatDate() + "'" +
            ", achatDescription='" + getAchatDescription() + "'" +
            ", achatQuantite=" + getAchatQuantite() +
            "}";
    }
}
