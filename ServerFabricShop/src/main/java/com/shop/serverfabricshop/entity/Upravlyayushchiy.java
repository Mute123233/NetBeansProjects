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
@Table(name = "upravlyayushchiy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Upravlyayushchiy.findAll", query = "SELECT u FROM Upravlyayushchiy u"),
    @NamedQuery(name = "Upravlyayushchiy.findById", query = "SELECT u FROM Upravlyayushchiy u WHERE u.id = :id")})
public class Upravlyayushchiy implements Serializable {

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

    public Upravlyayushchiy() {
    }

    public Upravlyayushchiy(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Upravlyayushchiy)) {
            return false;
        }
        Upravlyayushchiy other = (Upravlyayushchiy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Upravlyayushchiy[ id=" + id + " ]";
    }
    
}
