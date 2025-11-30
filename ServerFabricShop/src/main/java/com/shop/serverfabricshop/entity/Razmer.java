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
@Table(name = "razmer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Razmer.findAll", query = "SELECT r FROM Razmer r"),
    @NamedQuery(name = "Razmer.findById", query = "SELECT r FROM Razmer r WHERE r.id = :id"),
    @NamedQuery(name = "Razmer.findByShirina", query = "SELECT r FROM Razmer r WHERE r.shirina = :shirina"),
    @NamedQuery(name = "Razmer.findByDlina", query = "SELECT r FROM Razmer r WHERE r.dlina = :dlina")})
public class Razmer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "Shirina")
    private Integer shirina;
    @Column(name = "Dlina")
    private Integer dlina;
    @OneToMany(mappedBy = "idRazmer")
    private Collection<Postavkatkani> postavkatkaniCollection;
    @OneToMany(mappedBy = "idRazmer")
    private Collection<Prodazhatkani> prodazhatkaniCollection;

    public Razmer() {
    }

    public Razmer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShirina() {
        return shirina;
    }

    public void setShirina(Integer shirina) {
        this.shirina = shirina;
    }

    public Integer getDlina() {
        return dlina;
    }

    public void setDlina(Integer dlina) {
        this.dlina = dlina;
    }

    @XmlTransient
    public Collection<Postavkatkani> getPostavkatkaniCollection() {
        return postavkatkaniCollection;
    }

    public void setPostavkatkaniCollection(Collection<Postavkatkani> postavkatkaniCollection) {
        this.postavkatkaniCollection = postavkatkaniCollection;
    }

    @XmlTransient
    public Collection<Prodazhatkani> getProdazhatkaniCollection() {
        return prodazhatkaniCollection;
    }

    public void setProdazhatkaniCollection(Collection<Prodazhatkani> prodazhatkaniCollection) {
        this.prodazhatkaniCollection = prodazhatkaniCollection;
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
        if (!(object instanceof Razmer)) {
            return false;
        }
        Razmer other = (Razmer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Razmer[ id=" + id + " ]";
    }
    
}
