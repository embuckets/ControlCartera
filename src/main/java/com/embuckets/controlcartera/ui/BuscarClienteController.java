/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.SumaAseguradaAuto;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author emilio
 */
public class BuscarClienteController implements Initializable {

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField parternoField;
    @FXML
    private TextField maternoField;
    @FXML
    private TableView<Cliente> clienteTableView;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn paternoColumn;
    @FXML
    private TableColumn maternoColumn;
    @FXML
    private TextField clienteSelectedField;

    private Cliente cliente;
    private Dialog<Cliente> dialog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTablaAsegurados();
        crearDialog();
    }

    private void llenarTablaAsegurados() {
        clienteTableView.setItems(FXCollections.observableArrayList(getAllClientes()));

        nombreColumn.setCellValueFactory(new PropertyValueFactory("nombre"));
        paternoColumn.setCellValueFactory(new PropertyValueFactory("paterno"));
        maternoColumn.setCellValueFactory(new PropertyValueFactory("materno"));

        clienteTableView.setRowFactory((TableView<Cliente> param) -> {
            final TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked((event) -> {
                cliente = row.getItem();
                clienteSelectedField.setText(cliente.nombreProperty().get());
            });
//            row.onMouseClickedProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())));

            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    private void crearDialog() {
        dialog = new Dialog<>();
        dialog.setTitle("Buscar Cliente");
        ButtonType seleccionarButtonType = new ButtonType("Seleccionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(seleccionarButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(dialogPane);
        dialog.setResultConverter((dialogButton) -> {
            if (dialogButton == seleccionarButtonType) {
                return cliente;
            }
            return null;
        });
    }

    private List<Cliente> getAllClientes() {
        //TODO: leer asegurados de base de datos
        List<Cliente> clientes = new ArrayList<>();
        createClientesFalsos().forEach((asegurado) -> {
            clientes.add(asegurado.getCliente());
        });
        return clientes;
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void buscarCliente(KeyEvent event) {
        //TODO: buscar asegurado con campos del nombre incomplentos
    }

    @FXML
    private void seleccionar(ActionEvent event) {

    }

    @FXML
    private void buscarAsegurado(KeyEvent event) {
        //TODO: buscar asegurado con campos del nombre incomplentos
    }

    public Dialog<Cliente> getDialog() {
        return dialog;
    }

    private List<Asegurado> createClientesFalsos() {
        Asegurado asegurado1 = new Asegurado("emilio", "hernandez", "segovia");
        asegurado1.setIdcliente(1);
        asegurado1.getCliente().setIdcliente(1);
        asegurado1.getCliente().setNacimiento(LocalDate.of(1993, Month.MARCH, 2));
        asegurado1.setTipopersona(new TipoPersona("Fisica"));
        Asegurado asegurado2 = new Asegurado("daniel", "hernandez", "segovia");
        asegurado2.getCliente().setNacimiento(LocalDate.of(1993, Month.MARCH, 2));
        asegurado2.setIdcliente(2);
        asegurado2.getCliente().setIdcliente(2);
        asegurado2.setTipopersona(new TipoPersona("Fisica"));

        Poliza poliza1 = new Poliza();
        poliza1.setIdpoliza(1);
        poliza1.setNumero("numeor1");
        poliza1.setAseguradora(new Aseguradora("GNP"));
        poliza1.setRamo(new Ramo("vida"));
        poliza1.setProducto("producto");
        poliza1.setPlan("plan");
        poliza1.setPrima(new BigDecimal(21456));
        poliza1.setPrimamoneda(new Moneda("pesos"));
        poliza1.setIniciovigencia(java.util.Date.from(Instant.now().minus(Duration.ofDays(15))));
        poliza1.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        poliza1.setEstado(new EstadoPoliza("Vigente"));
        poliza1.setConductocobro(new ConductoCobro("agente"));
        poliza1.setFormapago(new FormaPago("mensual"));
        Cliente benef = new Cliente("beneficiario1", "hijo", "hijo");
        benef.setNacimiento(LocalDate.of(2016, Month.JANUARY, 9));
        poliza1.setPolizaVida(new PolizaVida(1));
        poliza1.getPolizaVida().setSumaasegurada(BigDecimal.valueOf(50000));
        poliza1.getPolizaVida().setSumaaseguradamoneda(new Moneda("Dolares"));
        poliza1.getPolizaVida().getClienteList().add(benef);
        poliza1.generarRecibos(3, new BigDecimal(10123.12), new BigDecimal(9123.12));

        Poliza poliza2 = new Poliza();
        poliza2.setIdpoliza(2);
        poliza2.setNumero("numeor2");
        poliza2.setAseguradora(new Aseguradora("GNP"));
        poliza2.setRamo(new Ramo("autos"));
        poliza2.setProducto("producto");
        poliza2.setPlan("plan");
        poliza2.setPrima(new BigDecimal(54789));
        poliza2.setPrimamoneda(new Moneda("pesos"));
        poliza2.setIniciovigencia(java.util.Date.from(Instant.now().minus(Duration.ofDays(20))));
        poliza2.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        poliza2.setEstado(new EstadoPoliza("Cancelada"));
        poliza2.setPolizaAuto(new PolizaAuto(2));
        poliza2.getPolizaAuto().setSumaaseguradaauto(new SumaAseguradaAuto("Factura"));
        poliza2.getPolizaAuto().getAutoList().add(new Auto(2, "STD 4PT RL", "VW", "Jetta", Year.of(2016)));
        poliza2.setConductocobro(new ConductoCobro("agente"));
        poliza2.setFormapago(new FormaPago("mensual"));
        poliza2.generarRecibos(4, new BigDecimal(10123.12), new BigDecimal(9123.12));

        poliza1.setContratante(asegurado1);
        poliza1.setTitular(asegurado1.getCliente());
        poliza2.setContratante(asegurado1);
        poliza2.setTitular(asegurado1.getCliente());
        asegurado1.getPolizaList().add(poliza1);
        asegurado1.getPolizaList().add(poliza2);

        Poliza poliza3 = new Poliza();
        poliza3.setIdpoliza(3);
        poliza3.setNumero("numeor3");
        poliza3.setAseguradora(new Aseguradora("PLAN SEGURO"));
        poliza3.setRamo(new Ramo("gastos medicos"));
        poliza3.setProducto("producto");
        poliza3.setPlan("plan");
        poliza3.setPrima(new BigDecimal(12456));
        poliza3.setPrimamoneda(new Moneda("PESOS"));
        poliza3.setIniciovigencia(java.util.Date.from(Instant.now().minus(Duration.ofDays(5))));
        poliza3.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        poliza3.setEstado(new EstadoPoliza("No vigente"));
        poliza3.setConductocobro(new ConductoCobro("agente"));
        poliza3.setFormapago(new FormaPago(Globals.FORMA_PAGO_TRIMESTRAL));
        poliza3.setPolizaGmm(new PolizaGmm(3, BigDecimal.valueOf(79654.12), "100000000", (short) 10));
        poliza3.getPolizaGmm().setDeduciblemoneda(new Moneda("PESOS"));
        poliza3.getPolizaGmm().setSumaaseguradamondeda(new Moneda("PESOS"));
        Cliente depend = new Cliente("beneficiario1", "hijo", "hijo");
        depend.setNacimiento(LocalDate.of(2016, Month.JANUARY, 9));
        poliza3.getPolizaGmm().getClienteList().add(depend);
        poliza3.generarRecibos(2, new BigDecimal(10123.12), new BigDecimal(9123.12));

        poliza3.setContratante(asegurado2);
        poliza3.setTitular(asegurado2.getCliente());
        asegurado2.getPolizaList().add(poliza3);

        List<Asegurado> list = new ArrayList<>();
        list.add(asegurado1);
        list.add(asegurado2);
        return list;
    }

}
