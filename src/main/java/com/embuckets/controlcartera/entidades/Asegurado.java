/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableCliente;
import com.embuckets.controlcartera.ui.observable.ObservableTreeItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
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
@Table(name = "ASEGURADO")
@NamedEntityGraphs({
    @NamedEntityGraph(
            name = "asegurado-graph-full-noDoc-noDom",
            attributeNodes = {
                @NamedAttributeNode("cliente"),
                @NamedAttributeNode("tipopersona"),
                @NamedAttributeNode("polizaList"),
                @NamedAttributeNode(value = "telefonoList", subgraph = "telef-subgraph"),
                @NamedAttributeNode(value = "emailList", subgraph = "email-subgraph"),},
            subgraphs = {
                @NamedSubgraph(
                        name = "telef-subgraph",
                        attributeNodes = {
                            @NamedAttributeNode("tipotelefono")
                        }
                ),
                @NamedSubgraph(
                        name = "email-subgraph",
                        attributeNodes = {
                            @NamedAttributeNode("tipoemail")
                        }
                )
            }
    ),
    @NamedEntityGraph(
            name = "asegurado-graph-cliente",
            attributeNodes = {
                @NamedAttributeNode("cliente")
            }
    ),
    @NamedEntityGraph(
            name = "asegurado-IncludeAll",
            includeAllAttributes = true)
})

@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asegurado.findAll", query = "SELECT a FROM Asegurado a"),
    @NamedQuery(name = "Asegurado.findByIdcliente", query = "SELECT a FROM Asegurado a WHERE a.idcliente = :idcliente"),
    @NamedQuery(name = "Asegurado.findByRfc", query = "SELECT a FROM Asegurado a WHERE a.rfc = :rfc"),
    @NamedQuery(name = "Asegurado.findByNota", query = "SELECT a FROM Asegurado a WHERE a.nota = :nota")})
public class Asegurado implements Serializable, ObservableTreeItem, ObservableCliente {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private Integer idcliente;
    @Column(name = "RFC")
    private String rfc;
    @Column(name = "NOTA")
    private String nota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asegurado", fetch = FetchType.LAZY)
    private List<Email> emailList;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = true, updatable = true)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Cliente cliente;
    @JoinColumn(name = "IDDOMICILIO", referencedColumnName = "IDDOMICILIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Domicilio iddomicilio;
    @JoinColumn(name = "TIPOPERSONA", referencedColumnName = "TIPOPERSONA")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoPersona tipopersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratante", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asegurado", fetch = FetchType.LAZY)
    private List<DocumentoAsegurado> documentoAseguradoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asegurado", fetch = FetchType.LAZY)
    private List<Telefono> telefonoList;

    public Asegurado() {
        emailList = new ArrayList<>();
        cliente = new Cliente();
        polizaList = new ArrayList<>();
        documentoAseguradoList = new ArrayList<>();
        telefonoList = new ArrayList<>();
    }

    public Asegurado(Integer idcliente) {
        this.idcliente = idcliente;
        emailList = new ArrayList<>();
        cliente = new Cliente();
        polizaList = new ArrayList<>();
        documentoAseguradoList = new ArrayList<>();
        telefonoList = new ArrayList<>();

    }

    public Asegurado(String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellidopaterno(apellidoPaterno);
        cliente.setApellidomaterno(apellidoMaterno);
        emailList = new ArrayList<>();
        polizaList = new ArrayList<>();
        documentoAseguradoList = new ArrayList<>();
        telefonoList = new ArrayList<>();
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @XmlTransient
    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getNombreCompleto() {
        return cliente.nombreProperty().get();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente.getIdcliente() != null) {
            setIdcliente(cliente.getIdcliente());
        }
    }

    public Domicilio getIddomicilio() {
        return iddomicilio;
    }

    public void setIddomicilio(Domicilio iddomicilio) {
        this.iddomicilio = iddomicilio;
    }

    public TipoPersona getTipopersona() {
        return tipopersona;
    }

    public void setTipopersona(TipoPersona tipopersona) {
        this.tipopersona = tipopersona;
    }

    @XmlTransient
    public List<Poliza> getPolizaList() {
        return polizaList;
    }

    public void setPolizaList(List<Poliza> polizaList) {
        this.polizaList = polizaList;
    }

    @XmlTransient
    public List<DocumentoAsegurado> getDocumentoAseguradoList() {
        return documentoAseguradoList;
    }

    public void setDocumentoAseguradoList(List<DocumentoAsegurado> documentoAseguradoList) {
        this.documentoAseguradoList = documentoAseguradoList;
    }

    @XmlTransient
    public List<Telefono> getTelefonoList() {
        return telefonoList;
    }

    public void setTelefonoList(List<Telefono> telefonoList) {
        this.telefonoList = telefonoList;
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
        if (!(object instanceof Asegurado)) {
            return false;
        }
        Asegurado other = (Asegurado) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Asegurado[ idcliente=" + idcliente + " ]";
    }

    public void agregarTelefono(Telefono telefono) {
        telefonoList.add(telefono);
    }

    public void agregarEmail(Email email) {
        emailList.add(email);
    }

    public void agregarDocumento(DocumentoAsegurado documentoAsegurado) {
        documentoAseguradoList.add(documentoAsegurado);
    }

    @Override
    public int getId() {
        return getIdcliente();
    }

    @Override
    public StringProperty nombreProperty() {
        return new SimpleStringProperty(cliente.getNombre() + " " + cliente.getApellidopaterno() + " " + cliente.getApellidomaterno());
    }

    @Override
    public StringProperty numeroProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public StringProperty aseguradoraProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public StringProperty ramoProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public StringProperty productoProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public StringProperty planProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public StringProperty primaProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public List<? extends ObservableTreeItem> getPolizaListProperty() {
        return getPolizaList();
    }

    @Override
    public StringProperty estadoProperty() {
        return new SimpleStringProperty("");
    }

    @Override
    public StringProperty primerNombreProperty() {
        return new SimpleStringProperty(cliente.getNombre());
    }

    @Override
    public StringProperty paternoProperty() {
        return new SimpleStringProperty(cliente.getApellidopaterno());
    }

    @Override
    public StringProperty maternoProperty() {
        return new SimpleStringProperty(cliente.getApellidomaterno());
    }

    @Override
    public StringProperty nacimientoProperty() {
        return new SimpleStringProperty(cliente.getNacimiento() == null ? "" : cliente.getNacimiento().toString());
    }
}
