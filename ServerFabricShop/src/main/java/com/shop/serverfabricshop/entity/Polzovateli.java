/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.entity;

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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 8277@stud.pgt.su
 */
@Entity
@Table(name = "polzovateli")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Polzovateli.findAll", query = "SELECT p FROM Polzovateli p"),
    @NamedQuery(name = "Polzovateli.findById", query = "SELECT p FROM Polzovateli p WHERE p.id = :id"),
    @NamedQuery(name = "Polzovateli.findByFamiliya", query = "SELECT p FROM Polzovateli p WHERE p.familiya = :familiya"),
    @NamedQuery(name = "Polzovateli.findByImya", query = "SELECT p FROM Polzovateli p WHERE p.imya = :imya"),
    @NamedQuery(name = "Polzovateli.findByOtchestvo", query = "SELECT p FROM Polzovateli p WHERE p.otchestvo = :otchestvo"),
    @NamedQuery(name = "Polzovateli.findByLogin", query = "SELECT p FROM Polzovateli p WHERE p.login = :login"),
    @NamedQuery(name = "Polzovateli.findByParol", query = "SELECT p FROM Polzovateli p WHERE p.parol = :parol"),
    @NamedQuery(name = "Polzovateli.findByNomertelefona", query = "SELECT p FROM Polzovateli p WHERE p.nomertelefona = :nomertelefona"),
    @NamedQuery(name = "Polzovateli.findByPasport", query = "SELECT p FROM Polzovateli p WHERE p.pasport = :pasport"),
    @NamedQuery(name = "Polzovateli.findByDatarozhdeniya", query = "SELECT p FROM Polzovateli p WHERE p.datarozhdeniya = :datarozhdeniya"),
    @NamedQuery(name = "Polzovateli.findByDatatrudoustroystva", query = "SELECT p FROM Polzovateli p WHERE p.datatrudoustroystva = :datatrudoustroystva"),
    @NamedQuery(name = "Polzovateli.findByFoto", query = "SELECT p FROM Polzovateli p WHERE p.foto = :foto")})
public class Polzovateli implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "familiya")
    private String familiya;
    @Basic(optional = false)
    @Column(name = "imya")
    private String imya;
    @Column(name = "otchestvo")
    private String otchestvo;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "parol")
    private String parol;
    @Column(name = "nomertelefona")
    private String nomertelefona;
    @Column(name = "pasport")
    private String pasport;
    @Column(name = "datarozhdeniya")
    @Temporal(TemporalType.DATE)
    private Date datarozhdeniya;
    @Column(name = "datatrudoustroystva")
    @Temporal(TemporalType.DATE)
    private Date datatrudoustroystva;
    @Column(name = "foto")
    private String foto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "polzovatelid")
    private Collection<Shveya> shveyaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "polzovatelid")
    private Collection<Prodavec> prodavecCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "polzovatelid")
    private Collection<Upravlyayushchiy> upravlyayushchiyCollection;

    public Polzovateli() {
    }

    public Polzovateli(Integer id) {
        this.id = id;
    }

    public Polzovateli(Integer id, String familiya, String imya, String login, String parol) {
        this.id = id;
        this.familiya = familiya;
        this.imya = imya;
        this.login = login;
        this.parol = parol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamiliya() {
        return familiya;
    }

    public void setFamiliya(String familiya) {
        this.familiya = familiya;
    }

    public String getImya() {
        return imya;
    }

    public void setImya(String imya) {
        this.imya = imya;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getParol() {
        return parol;
    }

    public void setParol(String parol) {
        this.parol = parol;
    }

    public String getNomertelefona() {
        return nomertelefona;
    }

    public void setNomertelefona(String nomertelefona) {
        this.nomertelefona = nomertelefona;
    }

    public String getPasport() {
        return pasport;
    }

    public void setPasport(String pasport) {
        this.pasport = pasport;
    }

    public Date getDatarozhdeniya() {
        return datarozhdeniya;
    }

    public void setDatarozhdeniya(Date datarozhdeniya) {
        this.datarozhdeniya = datarozhdeniya;
    }

    public Date getDatatrudoustroystva() {
        return datatrudoustroystva;
    }

    public void setDatatrudoustroystva(Date datatrudoustroystva) {
        this.datatrudoustroystva = datatrudoustroystva;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @XmlTransient
    public Collection<Shveya> getShveyaCollection() {
        return shveyaCollection;
    }

    public void setShveyaCollection(Collection<Shveya> shveyaCollection) {
        this.shveyaCollection = shveyaCollection;
    }

    @XmlTransient
    public Collection<Prodavec> getProdavecCollection() {
        return prodavecCollection;
    }

    public void setProdavecCollection(Collection<Prodavec> prodavecCollection) {
        this.prodavecCollection = prodavecCollection;
    }

    @XmlTransient
    public Collection<Upravlyayushchiy> getUpravlyayushchiyCollection() {
        return upravlyayushchiyCollection;
    }

    public void setUpravlyayushchiyCollection(Collection<Upravlyayushchiy> upravlyayushchiyCollection) {
        this.upravlyayushchiyCollection = upravlyayushchiyCollection;
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
        if (!(object instanceof Polzovateli)) {
            return false;
        }
        Polzovateli other = (Polzovateli) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.serverfabricshop.entity.Polzovateli[ id=" + id + " ]";
    }
    
}
