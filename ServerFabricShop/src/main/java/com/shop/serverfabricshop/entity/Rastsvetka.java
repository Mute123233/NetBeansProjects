/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "rastsvetka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rastsvetka.findAll", query = "SELECT r FROM Rastsvetka r"),
    @NamedQuery(name = "Rastsvetka.findById", query = "SELECT r FROM Rastsvetka r WHERE r.id = :id"),
    @NamedQuery(name = "Rastsvetka.findByNazvanie", query = "SELECT r FROM Rastsvetka r WHERE r.nazvanie = :nazvanie")})
public class Rastsvetka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nazvanie")
    private String nazvanie;
    @OneToMany(mappedBy = "idRastsvetka")
    private Collection<Tkan> tkanCollection;

    public Rastsvetka() {
    }

    public Rastsvetka(Integer id) {
        this.id = id;
    }

    public Rastsvetka(Integer id, String nazvanie) {
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

    @XmlTransient
    public Collection<Tkan> getTkanCollection() {
        return tkanCollection;
    }

    public void setTkanCollection(Collection<Tkan> tkanCollection) {
        this.tkanCollection = tkanCollection;
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
        if (!(object instanceof Rastsvetka)) {
            return false;
        }
        Rastsvetka other = (Rastsvetka) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Rastsvetka[ id=" + id + " ]";
    }
    
}
