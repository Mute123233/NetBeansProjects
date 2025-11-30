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
@Table(name = "sostavprocent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sostavprocent.findAll", query = "SELECT s FROM Sostavprocent s"),
    @NamedQuery(name = "Sostavprocent.findById", query = "SELECT s FROM Sostavprocent s WHERE s.id = :id"),
    @NamedQuery(name = "Sostavprocent.findByProcent", query = "SELECT s FROM Sostavprocent s WHERE s.procent = :procent")})
public class Sostavprocent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "procent")
    private int procent;
    @JsonIgnore
    @JoinColumn(name = "sostavid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sostav sostavid;
    @JsonIgnore
    @JoinColumn(name = "tkanyid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tkan tkanyid;

    public Sostavprocent() {
    }

    public Sostavprocent(Integer id) {
        this.id = id;
    }

    public Sostavprocent(Integer id, int procent) {
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

    public Sostav getSostavid() {
        return sostavid;
    }

    public void setSostavid(Sostav sostavid) {
        this.sostavid = sostavid;
    }

    public Tkan getTkanyid() {
        return tkanyid;
    }

    public void setTkanyid(Tkan tkanyid) {
        this.tkanyid = tkanyid;
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
        if (!(object instanceof Sostavprocent)) {
            return false;
        }
        Sostavprocent other = (Sostavprocent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Sostavprocent[ id=" + id + " ]";
    }
    
}
