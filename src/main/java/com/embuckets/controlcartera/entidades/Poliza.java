
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.ui.observable.ObservableRenovacion;
import com.embuckets.controlcartera.ui.observable.ObservableTreeItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
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
public class Poliza implements Serializable, ObservableTreeItem, ObservableRenovacion {

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
//    @Temporal(TemporalType.DATE)
    private LocalDate iniciovigencia;
    @Basic(optional = false)
    @Column(name = "FINVIGENCIA")
//    @Temporal(TemporalType.DATE)
    private LocalDate finvigencia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PRIMA")
    private BigDecimal prima;
    @Column(name = "NOTA")
    private String nota;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "poliza", fetch = FetchType.LAZY)
    private Caratula caratula;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "poliza", fetch = FetchType.LAZY)
    private PolizaAuto polizaAuto;
    @JoinColumn(name = "CONTRATANTE", referencedColumnName = "IDCLIENTE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asegurado contratante;
    @JoinColumn(name = "ASEGURADORA", referencedColumnName = "ASEGURADORA")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
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
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Moneda primamoneda;
    @JoinColumn(name = "RAMO", referencedColumnName = "RAMO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Ramo ramo;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "poliza", fetch = FetchType.LAZY)
    private PolizaVida polizaVida;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "poliza", fetch = FetchType.LAZY)
    private PolizaGmm polizaGmm;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpoliza", fetch = FetchType.LAZY)
//    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "idpoliza", fetch = FetchType.LAZY)
    private List<Recibo> reciboList;

    public Poliza() {
        this.reciboList = new ArrayList<>();
    }

    public Poliza(Integer idpoliza) {
        this.idpoliza = idpoliza;
        this.reciboList = new ArrayList<>();
    }

    public Poliza(Integer idpoliza, String numero, LocalDate iniciovigencia, LocalDate finvigencia, BigDecimal prima) {
        this.idpoliza = idpoliza;
        this.numero = numero;
        this.iniciovigencia = iniciovigencia;
        this.finvigencia = finvigencia;
        this.prima = prima;
        this.reciboList = new ArrayList<>();
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

    public LocalDate getIniciovigencia() {
        return iniciovigencia;
    }

    public void setIniciovigencia(LocalDate iniciovigencia) {
        this.iniciovigencia = iniciovigencia;
        this.finvigencia = iniciovigencia.plusMonths(12);
    }

//    public void setIniciovigencia(LocalDate iniciovigencia) {
//        this.iniciovigencia = iniciovigencia;
//    }
    public LocalDate getFinvigencia() {
        return finvigencia;
    }

    public void setFinvigencia(LocalDate finvigencia) {
        this.finvigencia = finvigencia;
        this.iniciovigencia = finvigencia.minusMonths(12);
    }

//    public void setFinvigencia(LocalDate finvigencia) {
//        this.finvigencia = Date.from(finvigencia.atStartOfDay(ZoneId.systemDefault()).toInstant());
//    }
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
        crearSubPoliza();
    }

    private void crearSubPoliza() {
        if (this.ramo.getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_AUTOS) || this.ramo.getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_FLOTILLA)) {
            this.polizaAuto = new PolizaAuto(idpoliza);
            polizaAuto.setIdpoliza(this.idpoliza);
            polizaAuto.setPoliza(this);
            polizaVida = null;
            polizaGmm = null;
        }
        if (this.ramo.getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_VIDA)) {
            this.polizaVida = new PolizaVida(idpoliza);
            polizaVida.setIdpoliza(this.idpoliza);
            polizaVida.setPoliza(this);
            polizaAuto = null;
            polizaGmm = null;
        }
        if (this.ramo.getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_GM)) {
            this.polizaGmm = new PolizaGmm(idpoliza);
            polizaGmm.setIdpoliza(this.idpoliza);
            polizaGmm.setPoliza(this);
            polizaVida = null;
            polizaAuto = null;
        }
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

    @Override
    public int getId() {
        return getIdpoliza();
    }

    @Override
    public StringProperty nombreProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public StringProperty numeroProperty() {
        return new SimpleStringProperty(numero);
    }

    @Override
    public StringProperty aseguradoraProperty() {
        return new SimpleStringProperty(aseguradora.getAseguradora());
    }

    @Override
    public StringProperty ramoProperty() {
        return new SimpleStringProperty(ramo.getRamo());
    }

    @Override
    public StringProperty productoProperty() {
        return new SimpleStringProperty(producto);
    }

    @Override
    public StringProperty planProperty() {
        return new SimpleStringProperty(plan);
    }

    @Override
    public StringProperty primaProperty() {
        return new SimpleStringProperty(Globals.formatCantidad(prima) + " " + primamoneda.getMoneda());
    }

    @Override
    public List<? extends ObservableTreeItem> getPolizaListProperty() {
        return null;
    }

    @Override
    public StringProperty aseguradoProperty() {
        return new SimpleStringProperty(this.contratante.getCliente().nombreProperty().get());
    }

    @Override
    public StringProperty polizaProperty() {
        return numeroProperty();
    }

    @Override
    public StringProperty finVigenciaProperty() {
        return new SimpleStringProperty(this.finvigencia.toString());
    }

    public StringProperty inicioVigenciaProperty() {
        return new SimpleStringProperty(this.iniciovigencia.toString());
    }

    @Override
    public StringProperty faltanProperty() {
        return new SimpleStringProperty("" + (finvigencia.getDayOfYear() - LocalDate.now().getDayOfYear()) + " días");
    }

    @Override
    public StringProperty estadoProperty() {
        return new SimpleStringProperty(estado.getEstado());
    }

    public void generarRecibos(int recibosPagados, BigDecimal importeConDerechoDePoliza, BigDecimal importeSubsecuente) {
        int recibos = cuantosRecibos();
        int siguienteMes = siguienteMes();
        LocalDate inicioVigenciaAnterior = this.iniciovigencia;
        LocalDate finVigenciaAnterior = inicioVigenciaAnterior.plusMonths(siguienteMes);
        Recibo recibo = new Recibo();
        recibo.setIdpoliza(this);
        recibo.setCubredesde(inicioVigenciaAnterior);
        recibo.setCubrehasta(finVigenciaAnterior);
        recibo.setImporte(importeConDerechoDePoliza);

        if (recibosPagados > 0) {
            recibo.setCobranza(new Cobranza(Globals.RECIBO_COBRANZA_PAGADO));
            recibosPagados--;
        } else {
            recibo.setCobranza(new Cobranza(Globals.RECIBO_COBRANZA_PENDIENTE));
        }

        this.reciboList.add(recibo);
        for (int i = 1; i < recibos; i++) {
            inicioVigenciaAnterior = finVigenciaAnterior;
            finVigenciaAnterior = finVigenciaAnterior.plusMonths(siguienteMes);
            recibo = new Recibo();
            recibo.setIdpoliza(this);
            recibo.setCubredesde(inicioVigenciaAnterior);
            recibo.setCubrehasta(finVigenciaAnterior);
            recibo.setImporte(importeSubsecuente);
            if (recibosPagados > 0) {
                recibo.setCobranza(new Cobranza(Globals.RECIBO_COBRANZA_PAGADO));
                recibosPagados--;
            } else {
                recibo.setCobranza(new Cobranza(Globals.RECIBO_COBRANZA_PENDIENTE));
            }
            this.reciboList.add(recibo);
        }
    }

    private int cuantosRecibos() {
        if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_ANUAL)) {
            return 1;
        } else if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_SEMESTRAL)) {
            return 2;
        } else if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_TRIMESTRAL)) {
            return 4;
        } else if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_MENSUAL)) {
            return 12;
        } else {
            return 1;
        }
    }

    private int siguienteMes() {
        if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_ANUAL)) {
            return 12;
        } else if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_SEMESTRAL)) {
            return 6;
        } else if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_TRIMESTRAL)) {
            return 3;
        } else if (this.formapago.getFormapago().equalsIgnoreCase(Globals.FORMA_PAGO_MENSUAL)) {
            return 1;
        } else {
            return 1;
        }
    }

}
