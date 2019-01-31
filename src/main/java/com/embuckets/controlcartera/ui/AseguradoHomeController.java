/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class AseguradoHomeController implements Initializable {

    @FXML
    private TextField textNombre;
    @FXML
    private TextField textPaterno;
    @FXML
    private TextField textMaterno;

    private Asegurado asegurado;
    private int aseguradoId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {

            //do stuff
            initData();

        });
    }

    /**
     * metodo para probar elpaso de parametros desde otro controlador
     *
     * @param asegurado
     */
    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    public void setAseguradoId(int id) {
        aseguradoId = id;
    }

    private void initData() {
        
//        StringProperty nombre = Main.getInstance().getNombre();
        textNombre.setText(asegurado.getCliente().getNombre());
//        
//        textNombre.setText(asegurado.getNombre());
//        textPaterno.setText(asegurado.getApellidoPaterno().orElse(""));
//        textMaterno.setText(asegurado.getApellidoMaterno().orElse(""));
//        System.out.println(asegurado);
    }

}
