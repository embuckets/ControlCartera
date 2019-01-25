/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "POLIZA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poliza.findAll", query = "SELECT p FROM Poliza p"),
    @NamedQuery(name = "Poliza.findByIdpoliza", query = "SELECT p FROM Poliza p WHERE p.idpoliza = :idpoliza"),
    @NamedQuery(name = "Poliza.findByNumero", query = "SELECT p FROM Poliza p WHERE p.numero = :numero"),
    @NamedQuery(name = "Poliza.findByProducto", query = "SELECT p FROM Poliza p WHERE p.producto = :producto"),
    @NamedQuery(name = "Poliza.findByPlan", query = "SELECT p FROM Poliza p WHERE p.plan = :plan"),
    @NamedQuery(name = "Poliza.findByIniciovigencia", query = "SELECT p FROM Poliza p WHERE p.iniciovigencia = :iniciovigencia"),
    @NamedQuery(name = "Poliza.findByFinvigencia", query = "SELECT p FROM Poliza p WHERE p.finvigencia = :finvigencia"),
    @NamedQuery(name = "Poliza.findByPrima", query = "SELECT p FROM Poliza p WHERE p.prima = :prima"),
    @NamedQuery(name = "Poliza.findByNota", query = "SELECT p FROM Poliza p WHERE p.nota = :nota")})
public class Poliza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPOLIZA")
    private Integer idpoliza;
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "PRODUCTO")
    private String producto;
    @Column(name = "PLAN")
    private String plan;
    @Basic(optional = false)
    @Column(name = "INICIOVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date iniciovigencia;
    @Basic(optional = false)
    @Column(name = "FINVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date finvigencia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PRIMA")
    private BigDecimal prima;
    @Column(name = "NOTA")
    private String nota;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "poliza", fetch = FetchType.LAZY)
    private Caratula caratula;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "poliza", fetch = FetchType.LAZY)
    private PolizaAuto polizaAuto;
    @JoinColumn(name = "CONTRATANTE", referencedColumnName = "IDCLIENTE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asegurado contratante;
    @JoinColumn(name = "ASEGURADORA", referencedColumnName = "ASEGURADORA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aseguradora aseguradora;
    @JoinColumn(name = "TITULAR", referencedColumnName = "IDCLIENTE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente titular;
    @JoinColumn(name = "CONDUCTOCOBRO", referencedColumnName = "CONDUCTOCOBRO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ConductoCobro conductocobro;
    @JoinColumn(name = "ESTADO", referencedColumnName = "ESTADO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoPoliza estado;
    @JoinColumn(name = "FORMAPAGO", referencedColumnName = "FORMAPAGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FormaPago formapago;
    @JoinColumn(name = "PRIMAMONEDA", referencedColumnName = "MONEDA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Moneda primamoneda;
    @JoinColumn(name = "RAMO", referencedColumnName = "RAMO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ramo ramo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "poliza", fetch = FetchType.LAZY)
    private PolizaVida polizaVida;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "poliza", fetch = FetchType.LAZY)
    private PolizaGmm polizaGmm;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpoliza", fetch = FetchType.LAZY)
    private List<Recibo> reciboList;

    public Poliza() {
    }

    public Poliza(Integer idpoliza) {
        this.idpoliza = idpoliza;
    }

    public Poliza(Integer idpoliza, String numero, Date iniciovigencia, Date finvigencia, BigDecimal prima) {
        this.idpoliza = idpoliza;
        this.numero = numero;
        this.iniciovigencia = iniciovigencia;
        this.finvigencia = finvigencia;
        this.prima = prima;
    }

    public Integer getIdpoliza() {
        return idpoliza;
    }

    public void setIdpoliza(Integer idpoliza) {
        this.idpoliza = idpoliza;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getIniciovigencia() {
        return iniciovigencia;
    }

    public void setIniciovigencia(Date iniciovigencia) {
        this.iniciovigencia = iniciovigencia;
    }

    public Date getFinvigencia() {
        return finvigencia;
    }

    public void setFinvigencia(Date finvigencia) {
        this.finvigencia = finvigencia;
    }

    public BigDecimal getPrima() {
        return prima;
    }

    public void setPrima(BigDecimal prima) {
        this.prima = prima;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Caratula getCaratula() {
        return caratula;
    }

    public void setCaratula(Caratula caratula) {
        this.caratula = caratula;
    }

    public PolizaAuto getPolizaAuto() {
        return polizaAuto;
    }

    public void setPolizaAuto(PolizaAuto polizaAuto) {
        this.polizaAuto = polizaAuto;
    }

    public Asegurado getContratante() {
        return contratante;
    }

    public void setContratante(Asegurado contratante) {
        this.contratante = contratante;
    }

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public ConductoCobro getConductocobro() {
        return conductocobro;
    }

    public void setConductocobro(ConductoCobro conductocobro) {
        this.conductocobro = conductocobro;
    }

    public EstadoPoliza getEstado() {
        return estado;
    }

    public void setEstado(EstadoPoliza estado) {
        this.estado = estado;
    }

    public FormaPago getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public Moneda getPrimamoneda() {
        return primamoneda;
    }

    public void setPrimamoneda(Moneda primamoneda) {
        this.primamoneda = primamoneda;
    }

    public Ramo getRamo() {
        return ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public PolizaVida getPolizaVida() {
        return polizaVida;
    }

    public void setPolizaVida(PolizaVida polizaVida) {
        this.polizaVida = polizaVida;
    }

    public PolizaGmm getPolizaGmm() {
        return polizaGmm;
    }

    public void setPolizaGmm(PolizaGmm polizaGmm) {
        this.polizaGmm = polizaGmm;
    }

    @XmlTransient
    public List<Recibo> getReciboList() {
        return reciboList;
    }

    public void setReciboList(List<Recibo> reciboList) {
        this.reciboList = reciboList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpoliza != null ? idpoliza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poliza)) {
            return false;
        }
        Poliza other = (Poliza) object;
        if ((this.idpoliza == null && other.idpoliza != null) || (this.idpoliza != null && !this.idpoliza.equals(other.idpoliza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Poliza[ idpoliza=" + idpoliza + " ]";
    }
    
}
