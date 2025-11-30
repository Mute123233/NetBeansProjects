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
@Table(name = "klient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Klient.findAll", query = "SELECT k FROM Klient k"),
    @NamedQuery(name = "Klient.findById", query = "SELECT k FROM Klient k WHERE k.id = :id"),
    @NamedQuery(name = "Klient.findByFamiliya", query = "SELECT k FROM Klient k WHERE k.familiya = :familiya"),
    @NamedQuery(name = "Klient.findByImya", query = "SELECT k FROM Klient k WHERE k.imya = :imya"),
    @NamedQuery(name = "Klient.findByOtchestvo", query = "SELECT k FROM Klient k WHERE k.otchestvo = :otchestvo"),
    @NamedQuery(name = "Klient.findByNomertelefona", query = "SELECT k FROM Klient k WHERE k.nomertelefona = :nomertelefona"),
    @NamedQuery(name = "Klient.findByBonusi", query = "SELECT k FROM Klient k WHERE k.bonusi = :bonusi")})
public class Klient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Familiya")
    private String familiya;
    @Basic(optional = false)
    @Column(name = "Imya")
    private String imya;
    @Column(name = "Otchestvo")
    private String otchestvo;
    @Column(name = "Nomertelefona")
    private String nomertelefona;
    @Column(name = "bonusi")
    private Integer bonusi;
    @OneToMany(mappedBy = "idKlient")
    private Collection<Zakaznaposhiv> zakaznaposhivCollection;

    public Klient() {
    }

    public Klient(Integer id) {
        this.id = id;
    }

    public Klient(Integer id, String familiya, String imya) {
        this.id = id;
        this.familiya = familiya;
        this.imya = imya;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamiliya() {
        return familiya;
    }

    public void setFamiliya(String familiya) {
        this.familiya = familiya;
    }

    public String getImya() {
        return imya;
    }

    public void setImya(String imya) {
        this.imya = imya;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public String getNomertelefona() {
        return nomertelefona;
    }

    public void setNomertelefona(String nomertelefona) {
        this.nomertelefona = nomertelefona;
    }

    public Integer getBonusi() {
        return bonusi;
    }

    public void setBonusi(Integer bonusi) {
        this.bonusi = bonusi;
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
        if (!(object instanceof Klient)) {
            return false;
        }
        Klient other = (Klient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Klient[ id=" + id + " ]";
    }
    
}
