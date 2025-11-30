/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
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
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "zakaztkan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakaztkan.findAll", query = "SELECT z FROM Zakaztkan z"),
    @NamedQuery(name = "Zakaztkan.findById", query = "SELECT z FROM Zakaztkan z WHERE z.id = :id"),
    @NamedQuery(name = "Zakaztkan.findByKolichestvoTkaniM", query = "SELECT z FROM Zakaztkan z WHERE z.kolichestvoTkaniM = :kolichestvoTkaniM")})
public class Zakaztkan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kolichestvoTkaniM")
    private BigDecimal kolichestvoTkaniM;
    @JsonIgnore
    @JoinColumn(name = "idTkan", referencedColumnName = "id")
    @ManyToOne
    private Tkan idTkan;
    @JsonIgnore
    @JoinColumn(name = "idZakaz", referencedColumnName = "id")
    @ManyToOne
    private Zakaznaposhiv idZakaz;

    public Zakaztkan() {
    }

    public Zakaztkan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getKolichestvoTkaniM() {
        return kolichestvoTkaniM;
    }

    public void setKolichestvoTkaniM(BigDecimal kolichestvoTkaniM) {
        this.kolichestvoTkaniM = kolichestvoTkaniM;
    }

    public Tkan getIdTkan() {
        return idTkan;
    }

    public void setIdTkan(Tkan idTkan) {
        this.idTkan = idTkan;
    }

    public Zakaznaposhiv getIdZakaz() {
        return idZakaz;
    }

    public void setIdZakaz(Zakaznaposhiv idZakaz) {
        this.idZakaz = idZakaz;
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
        if (!(object instanceof Zakaztkan)) {
            return false;
        }
        Zakaztkan other = (Zakaztkan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Zakaztkan[ id=" + id + " ]";
    }
    
}
