/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "izdelie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Izdelie.findAll", query = "SELECT i FROM Izdelie i"),
    @NamedQuery(name = "Izdelie.findById", query = "SELECT i FROM Izdelie i WHERE i.id = :id"),
    @NamedQuery(name = "Izdelie.findByNazvanie", query = "SELECT i FROM Izdelie i WHERE i.nazvanie = :nazvanie"),
    @NamedQuery(name = "Izdelie.findByTsena", query = "SELECT i FROM Izdelie i WHERE i.tsena = :tsena")})
public class Izdelie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Nazvanie")
    private String nazvanie;
    @Lob
    @Column(name = "Opisanie")
    private String opisanie;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Tsena")
    private BigDecimal tsena;
    @OneToMany(mappedBy = "idIzdelie")
    private Collection<Zakaznaposhiv> zakaznaposhivCollection;

    public Izdelie() {
    }

    public Izdelie(Integer id) {
        this.id = id;
    }

    public Izdelie(Integer id, String nazvanie) {
        this.id = id;
        this.nazvanie = nazvanie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazvanie() {
        return nazvanie;
    }

    public void setNazvanie(String nazvanie) {
        this.nazvanie = nazvanie;
    }

    public String getOpisanie() {
        return opisanie;
    }

    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }

    public BigDecimal getTsena() {
        return tsena;
    }

    public void setTsena(BigDecimal tsena) {
        this.tsena = tsena;
    }

    @XmlTransient
    public Collection<Zakaznaposhiv> getZakaznaposhivCollection() {
        return zakaznaposhivCollection;
    }

    public void setZakaznaposhivCollection(Collection<Zakaznaposhiv> zakaznaposhivCollection) {
        this.zakaznaposhivCollection = zakaznaposhivCollection;
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
        if (!(object instanceof Izdelie)) {
            return false;
        }
        Izdelie other = (Izdelie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Izdelie[ id=" + id + " ]";
    }
    
}
