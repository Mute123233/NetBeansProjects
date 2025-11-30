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
@Table(name = "tsvetfurnitury")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tsvetfurnitury.findAll", query = "SELECT t FROM Tsvetfurnitury t"),
    @NamedQuery(name = "Tsvetfurnitury.findById", query = "SELECT t FROM Tsvetfurnitury t WHERE t.id = :id"),
    @NamedQuery(name = "Tsvetfurnitury.findByNazvanie", query = "SELECT t FROM Tsvetfurnitury t WHERE t.nazvanie = :nazvanie")})
public class Tsvetfurnitury implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nazvanie")
    private String nazvanie;
    @OneToMany(mappedBy = "idTsvet")
    private Collection<Furnitura> furnituraCollection;

    public Tsvetfurnitury() {
    }

    public Tsvetfurnitury(Integer id) {
        this.id = id;
    }

    public Tsvetfurnitury(Integer id, String nazvanie) {
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
    public Collection<Furnitura> getFurnituraCollection() {
        return furnituraCollection;
    }

    public void setFurnituraCollection(Collection<Furnitura> furnituraCollection) {
        this.furnituraCollection = furnituraCollection;
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
        if (!(object instanceof Tsvetfurnitury)) {
            return false;
        }
        Tsvetfurnitury other = (Tsvetfurnitury) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Tsvetfurnitury[ id=" + id + " ]";
    }
    
}
