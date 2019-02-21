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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainApp extends Application {

    private Stage mainStage;
    private static MainApp instance;
    private Deque<Pair<Object, String>> windowStack;
//    private ControlCartera controlCartera;

    public MainApp() {
        instance = this;
        windowStack = new ArrayDeque<>();
    }

    public static MainApp getInstance() {
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(MainApp.class, (java.lang.String[]) null);
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
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeSceneContent(Object previousController, String previousFxml, String nextFxml) throws IOException {
        Parent page = (Parent) FXMLLoader.load(MainApp.class.getResource(nextFxml), null, new JavaFXBuilderFactory());
        windowStack.addLast(new Pair(previousController, previousFxml));
        Scene scene = mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
//            scene.getStylesheets().add(MainApp.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        } else {
            mainStage.getScene().setRoot(page);
        }
//        mainStage.setMaximized(true);
    }

    public void changeSceneContent(Object previousController, String previousFxml, Parent page, FXMLLoader loader) {
        windowStack.addLast(new Pair<>(previousController, previousFxml));
        Scene scene = mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
//            scene.getStylesheets().add(MainApp.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        } else {
            mainStage.getScene().setRoot(page);
            mainStage.show();
        }
    }

    public void goBack() throws IOException {
//        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AseguradoHome.fxml"), null, new JavaFXBuilderFactory());
//                            Parent parent = loader.load();
//                            AseguradoHomeController controller = loader.<AseguradoHomeController>getController();
//                            controller.setAsegurado((Asegurado) obs);
////            controller.setAseguradoId(id);
////        loader.setController(controller);
//                            MainApp.getInstance().changeSceneContent(this, location, parent, loader);
        
        
        Pair pair = windowStack.getLast();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource((String) pair.getValue()));
        Parent page = (Parent) loader.load();
        Controller controller = loader.<Controller>getController();
        controller.setData(((Controller)pair.getKey()).getData());
//        loader.setController(((Controller)pair.getKey()).getData());
//        Parent page = (Parent) loader.load(MainApp.class.getResource((String)pair.getValue()), null, new JavaFXBuilderFactory());
        Scene scene = mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
//            scene.getStylesheets().add(MainApp.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        } else {
            mainStage.getScene().setRoot(page);
        }
    }

    public void goHome() throws IOException {
        Parent page = (Parent) FXMLLoader.load(MainApp.class.getResource("/fxml/Home.fxml"), null, new JavaFXBuilderFactory());
        windowStack.clear();
        Scene scene = mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
//            scene.getStylesheets().add(MainApp.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        } else {
            mainStage.getScene().setRoot(page);
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Closing control cartera");
//        ControlCartera.getInstance().detenerBaseDeDatos();
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    public Stage getStage() {
        return mainStage;
    }

}
