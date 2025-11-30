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
@Table(name = "shveya")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shveya.findAll", query = "SELECT s FROM Shveya s"),
    @NamedQuery(name = "Shveya.findById", query = "SELECT s FROM Shveya s WHERE s.id = :id")})
public class Shveya implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JsonIgnore
    @JoinColumn(name = "polzovatelid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Polzovateli polzovatelid;
    @OneToMany(mappedBy = "idShveya")
    private Collection<Zakaznaposhiv> zakaznaposhivCollection;

    public Shveya() {
    }

    public Shveya(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Polzovateli getPolzovatelid() {
        return polzovatelid;
    }

    public void setPolzovatelid(Polzovateli polzovatelid) {
        this.polzovatelid = polzovatelid;
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
        if (!(object instanceof Shveya)) {
            return false;
        }
        Shveya other = (Shveya) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Shveya[ id=" + id + " ]";
    }
    
}
