/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demglaz.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "orders")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByIdorders", query = "SELECT o FROM Orders o WHERE o.idorders = :idorders"),
    @NamedQuery(name = "Orders.findByOrdernumber", query = "SELECT o FROM Orders o WHERE o.ordernumber = :ordernumber"),
    @NamedQuery(name = "Orders.findByOrderdate", query = "SELECT o FROM Orders o WHERE o.orderdate = :orderdate"),
    @NamedQuery(name = "Orders.findByDeliverydate", query = "SELECT o FROM Orders o WHERE o.deliverydate = :deliverydate")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorders")
    private Integer idorders;
    @Column(name = "ordernumber")
    private String ordernumber;
    @Basic(optional = false)
    @Column(name = "orderdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderdate;
    @Column(name = "deliverydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliverydate;
    @JsonIgnore @OneToMany(cascade = CascadeType.ALL, mappedBy = "idorder")
    private Collection<Orderitems> orderitemsCollection;
    @JoinColumn(name = "idstatus", referencedColumnName = "idorderstatuses")
    @ManyToOne(optional = false)
    private Orderstatuses idstatus;
    @JoinColumn(name = "idpickuppoint", referencedColumnName = "idpickuppoints")
    @ManyToOne(optional = false)
    private Pickuppoints idpickuppoint;
    @JoinColumn(name = "iduser", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private Users iduser;

    public Orders() {
    }

    public Orders(Integer idorders) {
        this.idorders = idorders;
    }

    public Orders(Integer idorders, Date orderdate) {
        this.idorders = idorders;
        this.orderdate = orderdate;
    }

    public Integer getIdorders() {
        return idorders;
    }

    public void setIdorders(Integer idorders) {
        this.idorders = idorders;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Date getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    public Collection<Orderitems> getOrderitemsCollection() {
        return orderitemsCollection;
    }

    public void setOrderitemsCollection(Collection<Orderitems> orderitemsCollection) {
        this.orderitemsCollection = orderitemsCollection;
    }

    public Orderstatuses getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(Orderstatuses idstatus) {
        this.idstatus = idstatus;
    }

    public Pickuppoints getIdpickuppoint() {
        return idpickuppoint;
    }

    public void setIdpickuppoint(Pickuppoints idpickuppoint) {
        this.idpickuppoint = idpickuppoint;
    }

    public Users getIduser() {
        return iduser;
    }

    public void setIduser(Users iduser) {
        this.iduser = iduser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorders != null ? idorders.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.idorders == null && other.idorders != null) || (this.idorders != null && !this.idorders.equals(other.idorders))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Orders[ idorders=" + idorders + " ]";
    }
    
}
