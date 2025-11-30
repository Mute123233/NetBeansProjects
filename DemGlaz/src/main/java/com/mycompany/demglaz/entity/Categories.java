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
@Table(name = "categories")
@NamedQueries({
    @NamedQuery(name = "Categories.findAll", query = "SELECT c FROM Categories c"),
    @NamedQuery(name = "Categories.findByIdcategories", query = "SELECT c FROM Categories c WHERE c.idcategories = :idcategories"),
    @NamedQuery(name = "Categories.findByName", query = "SELECT c FROM Categories c WHERE c.name = :name")})
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcategories")
    private Integer idcategories;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JsonIgnore @OneToMany(mappedBy = "idcategory")
    private Collection<Products> productsCollection;

    public Categories() {
    }

    public Categories(Integer idcategories) {
        this.idcategories = idcategories;
    }

    public Categories(Integer idcategories, String name) {
        this.idcategories = idcategories;
        this.name = name;
    }

    public Integer getIdcategories() {
        return idcategories;
    }

    public void setIdcategories(Integer idcategories) {
        this.idcategories = idcategories;
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
        hash += (idcategories != null ? idcategories.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categories)) {
            return false;
        }
        Categories other = (Categories) object;
        if ((this.idcategories == null && other.idcategories != null) || (this.idcategories != null && !this.idcategories.equals(other.idcategories))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Categories[ idcategories=" + idcategories + " ]";
    }
    
}
