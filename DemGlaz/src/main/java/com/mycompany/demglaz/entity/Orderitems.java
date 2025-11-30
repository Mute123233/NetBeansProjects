/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demglaz.entity;

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

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "orderitems")
@NamedQueries({
    @NamedQuery(name = "Orderitems.findAll", query = "SELECT o FROM Orderitems o"),
    @NamedQuery(name = "Orderitems.findByIdorderitems", query = "SELECT o FROM Orderitems o WHERE o.idorderitems = :idorderitems"),
    @NamedQuery(name = "Orderitems.findByQuantity", query = "SELECT o FROM Orderitems o WHERE o.quantity = :quantity")})
public class Orderitems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorderitems")
    private Integer idorderitems;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @JoinColumn(name = "idorder", referencedColumnName = "idorders")
    @ManyToOne(optional = false)
    private Orders idorder;
    @JoinColumn(name = "idproduct", referencedColumnName = "idproducts")
    @ManyToOne(optional = false)
    private Products idproduct;

    public Orderitems() {
    }

    public Orderitems(Integer idorderitems) {
        this.idorderitems = idorderitems;
    }

    public Orderitems(Integer idorderitems, int quantity) {
        this.idorderitems = idorderitems;
        this.quantity = quantity;
    }

    public Integer getIdorderitems() {
        return idorderitems;
    }

    public void setIdorderitems(Integer idorderitems) {
        this.idorderitems = idorderitems;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getIdorder() {
        return idorder;
    }

    public void setIdorder(Orders idorder) {
        this.idorder = idorder;
    }

    public Products getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(Products idproduct) {
        this.idproduct = idproduct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorderitems != null ? idorderitems.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderitems)) {
            return false;
        }
        Orderitems other = (Orderitems) object;
        if ((this.idorderitems == null && other.idorderitems != null) || (this.idorderitems != null && !this.idorderitems.equals(other.idorderitems))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Orderitems[ idorderitems=" + idorderitems + " ]";
    }
    
}
