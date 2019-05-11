/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableCliente;
import java.io.Serializable;
import java.time.LocalDate;
//import java.util.Date;
import java.util.List;
import java.util.Objects;
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
//    @Temporal(TemporalType.DATE)
    private LocalDate nacimiento;
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

    /**
     *
     */
    public Cliente() {
    }

    /**
     *
     * @param idcliente
     */
    public Cliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    /**
     *
     * @param idcliente
     * @param nombre
     */
    public Cliente(Integer idcliente, String nombre) {
        this.idcliente = idcliente;
        this.nombre = nombre;
    }

    /**
     *
     * @param nombre
     * @param paterno
     * @param materno
     */
    public Cliente(String nombre, String paterno, String materno) {
        this.nombre = nombre;
        this.apellidopaterno = paterno;
        this.apellidomaterno = materno;
    }

    /**
     *
     * @return
     */
    public Integer getIdcliente() {
        return idcliente;
    }

    /**
     *
     * @param idcliente
     */
    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getApellidopaterno() {
        return apellidopaterno;
    }

    /**
     *
     * @param apellidopaterno
     */
    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    /**
     *
     * @return
     */
    public String getApellidomaterno() {
        return apellidomaterno;
    }

    /**
     *
     * @param apellidomaterno
     */
    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    /**
     *
     * @return
     */
    public LocalDate getNacimiento() {
        return nacimiento;
    }

    /**
     *
     * @param nacimiento
     */
    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PolizaGmm> getPolizaGmmList() {
        return polizaGmmList;
    }

    /**
     *
     * @param polizaGmmList
     */
    public void setPolizaGmmList(List<PolizaGmm> polizaGmmList) {
        this.polizaGmmList = polizaGmmList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PolizaVida> getPolizaVidaList() {
        return polizaVidaList;
    }

    /**
     *
     * @param polizaVidaList
     */
    public void setPolizaVidaList(List<PolizaVida> polizaVidaList) {
        this.polizaVidaList = polizaVidaList;
    }

    /**
     *
     * @return
     */
    public Asegurado getAsegurado() {
        return asegurado;
    }

    /**
     *
     * @param asegurado
     */
    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    /**
     *
     * @return
     */
    public NotificacionCumple getNotificacionCumple() {
        return notificacionCumple;
    }

    /**
     *
     * @param notificacionCumple
     */
    public void setNotificacionCumple(NotificacionCumple notificacionCumple) {
        this.notificacionCumple = notificacionCumple;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Poliza> getPolizaList() {
        return polizaList;
    }

    /**
     *
     * @param polizaList
     */
    public void setPolizaList(List<Poliza> polizaList) {
        this.polizaList = polizaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Cliente)) {
//            return false;
//        }
//        Cliente other = (Cliente) object;
//        other.getIdcliente();
//        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.idcliente, other.idcliente)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Cliente[ idcliente=" + idcliente + " ]";
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty nombreProperty() {
        return new SimpleStringProperty(nombre + " " + apellidopaterno + " " + apellidomaterno);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty primerNombreProperty() {
        return new SimpleStringProperty(nombre);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty paternoProperty() {
        return new SimpleStringProperty(apellidopaterno);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty maternoProperty() {
        return new SimpleStringProperty(apellidomaterno);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty nacimientoProperty() {
        return new SimpleStringProperty(nacimiento == null ? "" : nacimiento.toString());
    }

}
