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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByIdproducts", query = "SELECT p FROM Products p WHERE p.idproducts = :idproducts"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByDescription", query = "SELECT p FROM Products p WHERE p.description = :description"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByStockquantity", query = "SELECT p FROM Products p WHERE p.stockquantity = :stockquantity"),
    @NamedQuery(name = "Products.findByDiscount", query = "SELECT p FROM Products p WHERE p.discount = :discount"),
    @NamedQuery(name = "Products.findByImage", query = "SELECT p FROM Products p WHERE p.image = :image")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducts")
    private Integer idproducts;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Basic(optional = false)
    @Column(name = "stockquantity")
    private int stockquantity;
    @Column(name = "discount")
    private Integer discount;
    @Column(name = "image")
    private String image;
    @JsonIgnore @OneToMany(cascade = CascadeType.ALL, mappedBy = "idproduct")
    private Collection<Orderitems> orderitemsCollection;
    @JoinColumn(name = "idcategory", referencedColumnName = "idcategories")
    @ManyToOne
    private Categories idcategory;
    @JoinColumn(name = "idmanufacturer", referencedColumnName = "idmanufacturers")
    @ManyToOne
    private Manufacturers idmanufacturer;
    @JoinColumn(name = "idsupplier", referencedColumnName = "idsuppliers")
    @ManyToOne
    private Suppliers idsupplier;
    @JoinColumn(name = "idunit", referencedColumnName = "idunits")
    @ManyToOne
    private Units idunit;

    public Products() {
    }

    public Products(Integer idproducts) {
        this.idproducts = idproducts;
    }

    public Products(Integer idproducts, String name, int stockquantity) {
        this.idproducts = idproducts;
        this.name = name;
        this.stockquantity = stockquantity;
    }

    public Integer getIdproducts() {
        return idproducts;
    }

    public void setIdproducts(Integer idproducts) {
        this.idproducts = idproducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStockquantity() {
        return stockquantity;
    }

    public void setStockquantity(int stockquantity) {
        this.stockquantity = stockquantity;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Collection<Orderitems> getOrderitemsCollection() {
        return orderitemsCollection;
    }

    public void setOrderitemsCollection(Collection<Orderitems> orderitemsCollection) {
        this.orderitemsCollection = orderitemsCollection;
    }

    public Categories getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(Categories idcategory) {
        this.idcategory = idcategory;
    }

    public Manufacturers getIdmanufacturer() {
        return idmanufacturer;
    }

    public void setIdmanufacturer(Manufacturers idmanufacturer) {
        this.idmanufacturer = idmanufacturer;
    }

    public Suppliers getIdsupplier() {
        return idsupplier;
    }

    public void setIdsupplier(Suppliers idsupplier) {
        this.idsupplier = idsupplier;
    }

    public Units getIdunit() {
        return idunit;
    }

    public void setIdunit(Units idunit) {
        this.idunit = idunit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducts != null ? idproducts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.idproducts == null && other.idproducts != null) || (this.idproducts != null && !this.idproducts.equals(other.idproducts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demglaz.entity.Products[ idproducts=" + idproducts + " ]";
    }
    
}
