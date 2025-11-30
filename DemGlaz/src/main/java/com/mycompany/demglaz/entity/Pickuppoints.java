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
@Table(name = "pickuppoints")
@NamedQueries({
    @NamedQuery(name = "Pickuppoints.findAll", query = "SELECT p FROM Pickuppoints p"),
    @NamedQuery(name = "Pickuppoints.findByIdpickuppoints", query = "SELECT p FROM Pickuppoints p WHERE p.idpickuppoints = :idpickuppoints"),
    @NamedQuery(name = "Pickuppoints.findByCity", query = "SELECT p FROM Pickuppoints p WHERE p.city = :city"),
    @NamedQuery(name = "Pickuppoints.findByStreet", query = "SELECT p FROM Pickuppoints p WHERE p.street = :street"),
    @NamedQuery(name = "Pickuppoints.findByHousenumber", query = "SELECT p FROM Pickuppoints p WHERE p.housenumber = :housenumber"),
    @NamedQuery(name = "Pickuppoints.findByName", query = "SELECT p FROM Pickuppoints p WHERE p.name = :name")})
public class Pickuppoints implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpickuppoints")
    private Integer idpickuppoints;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "street")
    private String street;
    @Basic(optional = false)
    @Column(name = "housenumber")
    private String housenumber;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JsonIgnore @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpickuppoint")
    private Collection<Orders> ordersCollection;

    public Pickuppoints() {
    }

    public Pickuppoints(Integer idpickuppoints) {
        this.idpickuppoints = idpickuppoints;
    }

    public Pickuppoints(Integer idpickuppoints, String city, String street, String housenumber, String name) {
        this.idpickuppoints = idpickuppoints;
        this.city = city;
        this.street = street;
        this.housenumber = housenumber;
        this.name = name;
    }

    public Integer getIdpickuppoints() {
        return idpickuppoints;
    }

    public void setIdpickuppoints(Integer idpickuppoints) {
        this.idpickuppoints = idpickuppoints;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
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
        hash += (idpickuppoints != null ? idpickuppoints.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pickuppoints)) {
            return false;
        }
        Pickuppoints other = (Pickuppoints) object;
        if ((this.idpickuppoints == null && other.idpickuppoints != null) || (this.idpickuppoints != null && !this.idpickuppoints.equals(other.idpickuppoints))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Pickuppoints[ idpickuppoints=" + idpickuppoints + " ]";
    }
    
}
