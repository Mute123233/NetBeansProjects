/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "furnitura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Furnitura.findAll", query = "SELECT f FROM Furnitura f"),
    @NamedQuery(name = "Furnitura.findById", query = "SELECT f FROM Furnitura f WHERE f.id = :id"),
    @NamedQuery(name = "Furnitura.findByArtikul", query = "SELECT f FROM Furnitura f WHERE f.artikul = :artikul"),
    @NamedQuery(name = "Furnitura.findByNazvanie", query = "SELECT f FROM Furnitura f WHERE f.nazvanie = :nazvanie"),
    @NamedQuery(name = "Furnitura.findByNovinka", query = "SELECT f FROM Furnitura f WHERE f.novinka = :novinka")})
public class Furnitura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "artikul")
    private String artikul;
    @Basic(optional = false)
    @Column(name = "nazvanie")
    private String nazvanie;
    @Column(name = "novinka")
    private Boolean novinka;
    @OneToMany(mappedBy = "idFurnitura")
    private Collection<Prodazhafurnitury> prodazhafurnituryCollection;
    @OneToMany(mappedBy = "idFurnitura")
    private Collection<Postavkafurnitury> postavkafurnituryCollection;
    @JsonIgnore
    @JoinColumn(name = "idEdIzmereniya", referencedColumnName = "id")
    @ManyToOne
    private Edinitsyizmereniya idEdIzmereniya;
    @JsonIgnore
    @JoinColumn(name = "idTsvet", referencedColumnName = "id")
    @ManyToOne
    private Tsvetfurnitury idTsvet;
    @OneToMany(mappedBy = "idFurnitura")
    private Collection<Zakazfurnitura> zakazfurnituraCollection;

    public Furnitura() {
    }

    public Furnitura(Integer id) {
        this.id = id;
    }

    public Furnitura(Integer id, String artikul, String nazvanie) {
        this.id = id;
        this.artikul = artikul;
        this.nazvanie = nazvanie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public String getNazvanie() {
        return nazvanie;
    }

    public void setNazvanie(String nazvanie) {
        this.nazvanie = nazvanie;
    }

    public Boolean getNovinka() {
        return novinka;
    }

    public void setNovinka(Boolean novinka) {
        this.novinka = novinka;
    }

    @XmlTransient
    public Collection<Prodazhafurnitury> getProdazhafurnituryCollection() {
        return prodazhafurnituryCollection;
    }

    public void setProdazhafurnituryCollection(Collection<Prodazhafurnitury> prodazhafurnituryCollection) {
        this.prodazhafurnituryCollection = prodazhafurnituryCollection;
    }

    @XmlTransient
    public Collection<Postavkafurnitury> getPostavkafurnituryCollection() {
        return postavkafurnituryCollection;
    }

    public void setPostavkafurnituryCollection(Collection<Postavkafurnitury> postavkafurnituryCollection) {
        this.postavkafurnituryCollection = postavkafurnituryCollection;
    }

    public Edinitsyizmereniya getIdEdIzmereniya() {
        return idEdIzmereniya;
    }

    public void setIdEdIzmereniya(Edinitsyizmereniya idEdIzmereniya) {
        this.idEdIzmereniya = idEdIzmereniya;
    }

    public Tsvetfurnitury getIdTsvet() {
        return idTsvet;
    }

    public void setIdTsvet(Tsvetfurnitury idTsvet) {
        this.idTsvet = idTsvet;
    }

    @XmlTransient
    public Collection<Zakazfurnitura> getZakazfurnituraCollection() {
        return zakazfurnituraCollection;
    }

    public void setZakazfurnituraCollection(Collection<Zakazfurnitura> zakazfurnituraCollection) {
        this.zakazfurnituraCollection = zakazfurnituraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Furnitura)) {
            return false;
        }
        Furnitura other = (Furnitura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Furnitura[ id=" + id + " ]";
    }
    
}
