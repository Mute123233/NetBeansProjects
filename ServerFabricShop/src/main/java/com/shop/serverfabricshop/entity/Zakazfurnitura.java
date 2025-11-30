/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
@Table(name = "zakazfurnitura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakazfurnitura.findAll", query = "SELECT z FROM Zakazfurnitura z"),
    @NamedQuery(name = "Zakazfurnitura.findById", query = "SELECT z FROM Zakazfurnitura z WHERE z.id = :id"),
    @NamedQuery(name = "Zakazfurnitura.findByKolichestvo", query = "SELECT z FROM Zakazfurnitura z WHERE z.kolichestvo = :kolichestvo")})
public class Zakazfurnitura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "kolichestvo")
    private Integer kolichestvo;
    @JsonIgnore
    @JoinColumn(name = "idFurnitura", referencedColumnName = "id")
    @ManyToOne
    private Furnitura idFurnitura;
    @JsonIgnore
    @JoinColumn(name = "idZakaz", referencedColumnName = "id")
    @ManyToOne
    private Zakaznaposhiv idZakaz;

    public Zakazfurnitura() {
    }

    public Zakazfurnitura(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKolichestvo() {
        return kolichestvo;
    }

    public void setKolichestvo(Integer kolichestvo) {
        this.kolichestvo = kolichestvo;
    }

    public Furnitura getIdFurnitura() {
        return idFurnitura;
    }

    public void setIdFurnitura(Furnitura idFurnitura) {
        this.idFurnitura = idFurnitura;
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
        if (!(object instanceof Zakazfurnitura)) {
            return false;
        }
        Zakazfurnitura other = (Zakazfurnitura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Zakazfurnitura[ id=" + id + " ]";
    }
    
}
