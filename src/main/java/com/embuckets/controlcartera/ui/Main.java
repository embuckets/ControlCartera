/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.embuckets.controlcartera.ui;

//import dominio.ControlCartera;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage mainStage;
    private static Main instance;
//    private ControlCartera controlCartera;
    
    public Main() {
        instance = this;
    }
    
    public static Main getInstance() {
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            
            //si existe base de datos ve a home.fxml
            // si no existe base de datos mostrar dialogo para importar de excel
            mainStage = primaryStage;
            Parent page = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Control de Cartera");
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changeSceneContent(String fxml) throws IOException {
        Parent page = (Parent) FXMLLoader.load(Main.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
//            scene.getStylesheets().add(Main.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        } else {
            mainStage.getScene().setRoot(page);
        }
//        mainStage.setMaximized(true);
    }
    
    public void changeSceneContent(Parent page) {
        Scene scene = mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
//            scene.getStylesheets().add(Main.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        } else {
            mainStage.getScene().setRoot(page);
            mainStage.show();
        }
        
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Closing control cartera");
//        ControlCartera.getInstance().detenerBaseDeDatos();
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
