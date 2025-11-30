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
@Table(name = "suppliers")
@NamedQueries({
    @NamedQuery(name = "Suppliers.findAll", query = "SELECT s FROM Suppliers s"),
    @NamedQuery(name = "Suppliers.findByIdsuppliers", query = "SELECT s FROM Suppliers s WHERE s.idsuppliers = :idsuppliers"),
    @NamedQuery(name = "Suppliers.findByName", query = "SELECT s FROM Suppliers s WHERE s.name = :name")})
public class Suppliers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsuppliers")
    private Integer idsuppliers;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JsonIgnore @OneToMany(mappedBy = "idsupplier")
    private Collection<Products> productsCollection;

    public Suppliers() {
    }

    public Suppliers(Integer idsuppliers) {
        this.idsuppliers = idsuppliers;
    }

    public Suppliers(Integer idsuppliers, String name) {
        this.idsuppliers = idsuppliers;
        this.name = name;
    }

    public Integer getIdsuppliers() {
        return idsuppliers;
    }

    public void setIdsuppliers(Integer idsuppliers) {
        this.idsuppliers = idsuppliers;
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
        hash += (idsuppliers != null ? idsuppliers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suppliers)) {
            return false;
        }
        Suppliers other = (Suppliers) object;
        if ((this.idsuppliers == null && other.idsuppliers != null) || (this.idsuppliers != null && !this.idsuppliers.equals(other.idsuppliers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Suppliers[ idsuppliers=" + idsuppliers + " ]";
    }
    
}
