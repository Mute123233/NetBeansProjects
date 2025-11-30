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
@Table(name = "manufacturers")
@NamedQueries({
    @NamedQuery(name = "Manufacturers.findAll", query = "SELECT m FROM Manufacturers m"),
    @NamedQuery(name = "Manufacturers.findByIdmanufacturers", query = "SELECT m FROM Manufacturers m WHERE m.idmanufacturers = :idmanufacturers"),
    @NamedQuery(name = "Manufacturers.findByName", query = "SELECT m FROM Manufacturers m WHERE m.name = :name")})
public class Manufacturers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmanufacturers")
    private Integer idmanufacturers;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JsonIgnore @OneToMany(mappedBy = "idmanufacturer")
    private Collection<Products> productsCollection;

    public Manufacturers() {
    }

    public Manufacturers(Integer idmanufacturers) {
        this.idmanufacturers = idmanufacturers;
    }

    public Manufacturers(Integer idmanufacturers, String name) {
        this.idmanufacturers = idmanufacturers;
        this.name = name;
    }

    public Integer getIdmanufacturers() {
        return idmanufacturers;
    }

    public void setIdmanufacturers(Integer idmanufacturers) {
        this.idmanufacturers = idmanufacturers;
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
        hash += (idmanufacturers != null ? idmanufacturers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manufacturers)) {
            return false;
        }
        Manufacturers other = (Manufacturers) object;
        if ((this.idmanufacturers == null && other.idmanufacturers != null) || (this.idmanufacturers != null && !this.idmanufacturers.equals(other.idmanufacturers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Manufacturers[ idmanufacturers=" + idmanufacturers + " ]";
    }
    
}
