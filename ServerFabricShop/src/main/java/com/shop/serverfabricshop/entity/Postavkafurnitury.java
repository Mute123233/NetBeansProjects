/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "postavkafurnitury")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Postavkafurnitury.findAll", query = "SELECT p FROM Postavkafurnitury p"),
    @NamedQuery(name = "Postavkafurnitury.findById", query = "SELECT p FROM Postavkafurnitury p WHERE p.id = :id"),
    @NamedQuery(name = "Postavkafurnitury.findByData", query = "SELECT p FROM Postavkafurnitury p WHERE p.data = :data"),
    @NamedQuery(name = "Postavkafurnitury.findByKolichestvo", query = "SELECT p FROM Postavkafurnitury p WHERE p.kolichestvo = :kolichestvo"),
    @NamedQuery(name = "Postavkafurnitury.findByTsenaZaYedinitsu", query = "SELECT p FROM Postavkafurnitury p WHERE p.tsenaZaYedinitsu = :tsenaZaYedinitsu")})
public class Postavkafurnitury implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "Kolichestvo")
    private Integer kolichestvo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tsenaZaYedinitsu")
    private BigDecimal tsenaZaYedinitsu;
    @JsonIgnore
    @JoinColumn(name = "idFurnitura", referencedColumnName = "id")
    @ManyToOne
    private Furnitura idFurnitura;

    public Postavkafurnitury() {
    }

    public Postavkafurnitury(Integer id) {
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

    public Integer getKolichestvo() {
        return kolichestvo;
    }

    public void setKolichestvo(Integer kolichestvo) {
        this.kolichestvo = kolichestvo;
    }

    public BigDecimal getTsenaZaYedinitsu() {
        return tsenaZaYedinitsu;
    }

    public void setTsenaZaYedinitsu(BigDecimal tsenaZaYedinitsu) {
        this.tsenaZaYedinitsu = tsenaZaYedinitsu;
    }

    public Furnitura getIdFurnitura() {
        return idFurnitura;
    }

    public void setIdFurnitura(Furnitura idFurnitura) {
        this.idFurnitura = idFurnitura;
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
        if (!(object instanceof Postavkafurnitury)) {
            return false;
        }
        Postavkafurnitury other = (Postavkafurnitury) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Postavkafurnitury[ id=" + id + " ]";
    }
    
}
