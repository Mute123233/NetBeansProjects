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
@Table(name = "skidka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skidka.findAll", query = "SELECT s FROM Skidka s"),
    @NamedQuery(name = "Skidka.findById", query = "SELECT s FROM Skidka s WHERE s.id = :id"),
    @NamedQuery(name = "Skidka.findByProcent", query = "SELECT s FROM Skidka s WHERE s.procent = :procent")})
public class Skidka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "procent")
    private int procent;
    @OneToMany(mappedBy = "idSkidka")
    private Collection<Zakaznaposhiv> zakaznaposhivCollection;

    public Skidka() {
    }

    public Skidka(Integer id) {
        this.id = id;
    }

    public Skidka(Integer id, int procent) {
        this.id = id;
        this.procent = procent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProcent() {
        return procent;
    }

    public void setProcent(int procent) {
        this.procent = procent;
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
        if (!(object instanceof Skidka)) {
            return false;
        }
        Skidka other = (Skidka) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Skidka[ id=" + id + " ]";
    }
    
}
