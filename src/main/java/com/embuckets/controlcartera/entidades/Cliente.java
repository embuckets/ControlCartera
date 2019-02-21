/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableCliente;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdcliente", query = "SELECT c FROM Cliente c WHERE c.idcliente = :idcliente"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByApellidopaterno", query = "SELECT c FROM Cliente c WHERE c.apellidopaterno = :apellidopaterno"),
    @NamedQuery(name = "Cliente.findByApellidomaterno", query = "SELECT c FROM Cliente c WHERE c.apellidomaterno = :apellidomaterno"),
    @NamedQuery(name = "Cliente.findByNacimiento", query = "SELECT c FROM Cliente c WHERE c.nacimiento = :nacimiento")})
public class Cliente implements Serializable, ObservableCliente {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private Integer idcliente;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDOPATERNO")
    private String apellidopaterno;
    @Column(name = "APELLIDOMATERNO")
    private String apellidomaterno;
    @Column(name = "NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date nacimiento;
    @JoinTable(name = "DEPENDIENTE", joinColumns = {
        @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")}, inverseJoinColumns = {
        @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<PolizaGmm> polizaGmmList;
    @JoinTable(name = "BENEFICIARIO", joinColumns = {
        @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")}, inverseJoinColumns = {
        @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<PolizaVida> polizaVidaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY)
    private Asegurado asegurado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY)
    private NotificacionCumple notificacionCumple;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "titular", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;

    public Cliente() {
    }

    public Cliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Cliente(Integer idcliente, String nombre) {
        this.idcliente = idcliente;
        this.nombre = nombre;
    }

    public Cliente(String nombre, String paterno, String materno) {
        this.nombre = nombre;
        this.apellidopaterno = paterno;
        this.apellidomaterno = materno;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public LocalDate getNacimientoLocalDate() {
        return nacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = Date.from(nacimiento.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @XmlTransient
    public List<PolizaGmm> getPolizaGmmList() {
        return polizaGmmList;
    }

    public void setPolizaGmmList(List<PolizaGmm> polizaGmmList) {
        this.polizaGmmList = polizaGmmList;
    }

    @XmlTransient
    public List<PolizaVida> getPolizaVidaList() {
        return polizaVidaList;
    }

    public void setPolizaVidaList(List<PolizaVida> polizaVidaList) {
        this.polizaVidaList = polizaVidaList;
    }

    public Asegurado getAsegurado() {
        return asegurado;
    }

    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    public NotificacionCumple getNotificacionCumple() {
        return notificacionCumple;
    }

    public void setNotificacionCumple(NotificacionCumple notificacionCumple) {
        this.notificacionCumple = notificacionCumple;
    }

    @XmlTransient
    public List<Poliza> getPolizaList() {
        return polizaList;
    }

    public void setPolizaList(List<Poliza> polizaList) {
        this.polizaList = polizaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Cliente[ idcliente=" + idcliente + " ]";
    }

    @Override
    public StringProperty nombreProperty() {
        return new SimpleStringProperty(nombre + " " + apellidopaterno + " " + apellidomaterno);
    }

    @Override
    public StringProperty nacimientoProperty() {
        return new SimpleStringProperty(nacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
    }

}
