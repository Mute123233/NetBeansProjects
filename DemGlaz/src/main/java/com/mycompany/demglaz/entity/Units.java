/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demglaz.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "units")
@NamedQueries({
    @NamedQuery(name = "Units.findAll", query = "SELECT u FROM Units u"),
    @NamedQuery(name = "Units.findByIdunits", query = "SELECT u FROM Units u WHERE u.idunits = :idunits"),
    @NamedQuery(name = "Units.findByName", query = "SELECT u FROM Units u WHERE u.name = :name")})
public class Units implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idunits")
    private Integer idunits;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JsonIgnore @OneToMany(mappedBy = "idunit")
    private Collection<Products> productsCollection;

    public Units() {
    }

    public Units(Integer idunits) {
        this.idunits = idunits;
    }

    public Units(Integer idunits, String name) {
        this.idunits = idunits;
        this.name = name;
    }

    public Integer getIdunits() {
        return idunits;
    }

    public void setIdunits(Integer idunits) {
        this.idunits = idunits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idunits != null ? idunits.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Units)) {
            return false;
        }
        Units other = (Units) object;
        if ((this.idunits == null && other.idunits != null) || (this.idunits != null && !this.idunits.equals(other.idunits))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Units[ idunits=" + idunits + " ]";
    }
    
}
