/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demglaz.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "orderstatuses")
@NamedQueries({
    @NamedQuery(name = "Orderstatuses.findAll", query = "SELECT o FROM Orderstatuses o"),
    @NamedQuery(name = "Orderstatuses.findByIdorderstatuses", query = "SELECT o FROM Orderstatuses o WHERE o.idorderstatuses = :idorderstatuses"),
    @NamedQuery(name = "Orderstatuses.findByName", query = "SELECT o FROM Orderstatuses o WHERE o.name = :name")})
public class Orderstatuses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorderstatuses")
    private Integer idorderstatuses;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JsonIgnore @OneToMany(cascade = CascadeType.ALL, mappedBy = "idstatus")
    private Collection<Orders> ordersCollection;

    public Orderstatuses() {
    }

    public Orderstatuses(Integer idorderstatuses) {
        this.idorderstatuses = idorderstatuses;
    }

    public Orderstatuses(Integer idorderstatuses, String name) {
        this.idorderstatuses = idorderstatuses;
        this.name = name;
    }

    public Integer getIdorderstatuses() {
        return idorderstatuses;
    }

    public void setIdorderstatuses(Integer idorderstatuses) {
        this.idorderstatuses = idorderstatuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorderstatuses != null ? idorderstatuses.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderstatuses)) {
            return false;
        }
        Orderstatuses other = (Orderstatuses) object;
        if ((this.idorderstatuses == null && other.idorderstatuses != null) || (this.idorderstatuses != null && !this.idorderstatuses.equals(other.idorderstatuses))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Orderstatuses[ idorderstatuses=" + idorderstatuses + " ]";
    }
    
}
