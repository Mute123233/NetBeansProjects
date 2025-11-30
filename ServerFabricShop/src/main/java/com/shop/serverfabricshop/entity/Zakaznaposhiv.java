/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "zakaznaposhiv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakaznaposhiv.findAll", query = "SELECT z FROM Zakaznaposhiv z"),
    @NamedQuery(name = "Zakaznaposhiv.findById", query = "SELECT z FROM Zakaznaposhiv z WHERE z.id = :id"),
    @NamedQuery(name = "Zakaznaposhiv.findByData", query = "SELECT z FROM Zakaznaposhiv z WHERE z.data = :data"),
    @NamedQuery(name = "Zakaznaposhiv.findByTsenaRabotyShvei", query = "SELECT z FROM Zakaznaposhiv z WHERE z.tsenaRabotyShvei = :tsenaRabotyShvei")})
public class Zakaznaposhiv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "Data")
    @Temporal(TemporalType.DATE)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TsenaRabotyShvei")
    private BigDecimal tsenaRabotyShvei;
    @JsonIgnore
    @JoinColumn(name = "idIzdelie", referencedColumnName = "id")
    @ManyToOne
    private Izdelie idIzdelie;
    @JsonIgnore
    @JoinColumn(name = "idKlient", referencedColumnName = "id")
    @ManyToOne
    private Klient idKlient;
    @JsonIgnore
    @JoinColumn(name = "idShveya", referencedColumnName = "id")
    @ManyToOne
    private Shveya idShveya;
    @JsonIgnore
    @JoinColumn(name = "idSkidka", referencedColumnName = "id")
    @ManyToOne
    private Skidka idSkidka;
    @JsonIgnore
    @JoinColumn(name = "idStatusRaboty", referencedColumnName = "id")
    @ManyToOne
    private Statusraboty idStatusRaboty;
    @OneToMany(mappedBy = "idZakaz")
    private Collection<Zakazfurnitura> zakazfurnituraCollection;
    @OneToMany(mappedBy = "idZakaz")
    private Collection<Zakaztkan> zakaztkanCollection;

    public Zakaznaposhiv() {
    }

    public Zakaznaposhiv(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getTsenaRabotyShvei() {
        return tsenaRabotyShvei;
    }

    public void setTsenaRabotyShvei(BigDecimal tsenaRabotyShvei) {
        this.tsenaRabotyShvei = tsenaRabotyShvei;
    }

    public Izdelie getIdIzdelie() {
        return idIzdelie;
    }

    public void setIdIzdelie(Izdelie idIzdelie) {
        this.idIzdelie = idIzdelie;
    }

    public Klient getIdKlient() {
        return idKlient;
    }

    public void setIdKlient(Klient idKlient) {
        this.idKlient = idKlient;
    }

    public Shveya getIdShveya() {
        return idShveya;
    }

    public void setIdShveya(Shveya idShveya) {
        this.idShveya = idShveya;
    }

    public Skidka getIdSkidka() {
        return idSkidka;
    }

    public void setIdSkidka(Skidka idSkidka) {
        this.idSkidka = idSkidka;
    }

    public Statusraboty getIdStatusRaboty() {
        return idStatusRaboty;
    }

    public void setIdStatusRaboty(Statusraboty idStatusRaboty) {
        this.idStatusRaboty = idStatusRaboty;
    }

    @XmlTransient
    public Collection<Zakazfurnitura> getZakazfurnituraCollection() {
        return zakazfurnituraCollection;
    }

    public void setZakazfurnituraCollection(Collection<Zakazfurnitura> zakazfurnituraCollection) {
        this.zakazfurnituraCollection = zakazfurnituraCollection;
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
        if (!(object instanceof Zakaznaposhiv)) {
            return false;
        }
        Zakaznaposhiv other = (Zakaznaposhiv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Zakaznaposhiv[ id=" + id + " ]";
    }
    
}
