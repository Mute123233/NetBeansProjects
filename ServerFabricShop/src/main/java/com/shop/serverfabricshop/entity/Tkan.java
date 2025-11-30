/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
@Table(name = "tkan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tkan.findAll", query = "SELECT t FROM Tkan t"),
    @NamedQuery(name = "Tkan.findById", query = "SELECT t FROM Tkan t WHERE t.id = :id"),
    @NamedQuery(name = "Tkan.findByArtikul", query = "SELECT t FROM Tkan t WHERE t.artikul = :artikul"),
    @NamedQuery(name = "Tkan.findByNazvanie", query = "SELECT t FROM Tkan t WHERE t.nazvanie = :nazvanie"),
    @NamedQuery(name = "Tkan.findByKategoriya", query = "SELECT t FROM Tkan t WHERE t.kategoriya = :kategoriya"),
    @NamedQuery(name = "Tkan.findByNovinka", query = "SELECT t FROM Tkan t WHERE t.novinka = :novinka"),
    @NamedQuery(name = "Tkan.findByFoto", query = "SELECT t FROM Tkan t WHERE t.foto = :foto")})
public class Tkan implements Serializable {

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
    @Column(name = "kategoriya")
    private String kategoriya;
    @Column(name = "novinka")
    private Boolean novinka;
    @Column(name = "foto")
    private String foto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkanyid")
    private Collection<Sostavprocent> sostavprocentCollection;
    @OneToMany(mappedBy = "idTkan")
    private Collection<Postavkatkani> postavkatkaniCollection;
    @JsonIgnore
    @JoinColumn(name = "idRastsvetka", referencedColumnName = "id")
    @ManyToOne
    private Rastsvetka idRastsvetka;
    @JsonIgnore
    @JoinColumn(name = "idTsvet", referencedColumnName = "id")
    @ManyToOne
    private Tsvettkani idTsvet;
    @OneToMany(mappedBy = "idTkan")
    private Collection<Prodazhatkani> prodazhatkaniCollection;
    @OneToMany(mappedBy = "idTkan")
    private Collection<Zakaztkan> zakaztkanCollection;

    public Tkan() {
    }

    public Tkan(Integer id) {
        this.id = id;
    }

    public Tkan(Integer id, String artikul, String nazvanie) {
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

    public String getKategoriya() {
        return kategoriya;
    }

    public void setKategoriya(String kategoriya) {
        this.kategoriya = kategoriya;
    }

    public Boolean getNovinka() {
        return novinka;
    }

    public void setNovinka(Boolean novinka) {
        this.novinka = novinka;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @XmlTransient
    public Collection<Sostavprocent> getSostavprocentCollection() {
        return sostavprocentCollection;
    }

    public void setSostavprocentCollection(Collection<Sostavprocent> sostavprocentCollection) {
        this.sostavprocentCollection = sostavprocentCollection;
    }

    @XmlTransient
    public Collection<Postavkatkani> getPostavkatkaniCollection() {
        return postavkatkaniCollection;
    }

    public void setPostavkatkaniCollection(Collection<Postavkatkani> postavkatkaniCollection) {
        this.postavkatkaniCollection = postavkatkaniCollection;
    }

    public Rastsvetka getIdRastsvetka() {
        return idRastsvetka;
    }

    public void setIdRastsvetka(Rastsvetka idRastsvetka) {
        this.idRastsvetka = idRastsvetka;
    }

    public Tsvettkani getIdTsvet() {
        return idTsvet;
    }

    public void setIdTsvet(Tsvettkani idTsvet) {
        this.idTsvet = idTsvet;
    }

    @XmlTransient
    public Collection<Prodazhatkani> getProdazhatkaniCollection() {
        return prodazhatkaniCollection;
    }

    public void setProdazhatkaniCollection(Collection<Prodazhatkani> prodazhatkaniCollection) {
        this.prodazhatkaniCollection = prodazhatkaniCollection;
    }

    @XmlTransient
    public Collection<Zakaztkan> getZakaztkanCollection() {
        return zakaztkanCollection;
    }

    public void setZakaztkanCollection(Collection<Zakaztkan> zakaztkanCollection) {
        this.zakaztkanCollection = zakaztkanCollection;
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
        if (!(object instanceof Tkan)) {
            return false;
        }
        Tkan other = (Tkan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Tkan[ id=" + id + " ]";
    }
    
}
