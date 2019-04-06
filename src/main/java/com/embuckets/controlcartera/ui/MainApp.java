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
import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

//import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MainApp extends Application {

    private static final Logger logger = LogManager.getLogger(MainApp.class);

    private Stage mainStage;
    private static MainApp instance;
    private Deque<Pair<Object, String>> windowStack;
    private BaseDeDatos bd;
    private Agente agente;
//    private ControlCartera controlCartera;

    public MainApp() {
        instance = this;
        windowStack = new ArrayDeque<>();
        this.bd = BaseDeDatos.getInstance();
        try {
            this.agente = Agente.getInstance();
        } catch (IOException ex) {
            logger.fatal("Error al cargar al cliente", ex);
            this.stop();
        }
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
            mainStage = primaryStage;
            mainStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/icons/cartera.png")));
            Parent page = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Control de Cartera");
            primaryStage.setMaximized(true);
            primaryStage.show();
            logger.info("Application started");
        } catch (Exception ex) {
            logger.error("Error al correr aplicacion: ", ex);
        }
    }

    public void changeSceneContent(Object previousController, String previousFxml, String nextFxml) throws IOException {
        Parent page = (Parent) FXMLLoader.load(MainApp.class.getResource(nextFxml), null, new JavaFXBuilderFactory());
        if (!(previousFxml.equals("/fxml/RenovarPoliza.fxml") && nextFxml.equals("/fxml/PolizaHome.fxml"))) {
            windowStack.addLast(new Pair(previousController, previousFxml));
        }
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
        if (!(previousFxml.equals("/fxml/RenovarPoliza.fxml") && loader.getLocation().getFile().endsWith("/fxml/PolizaHome.fxml"))) {
            windowStack.addLast(new Pair(previousController, previousFxml));
        }
//        windowStack.addLast(new Pair<>(previousController, previousFxml));
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
        Pair pair = windowStack.pollLast();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource((String) pair.getValue()));
        Parent page = (Parent) loader.load();
        Controller controller = loader.<Controller>getController();
        controller.setData(((Controller) pair.getKey()).getData());
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
    public void stop() {
        logger.info("Stopping Control de Cartera");
        bd.close();
        LogManager.shutdown();
    }

    public Stage getStage() {
        return mainStage;
    }

    public BaseDeDatos getBaseDeDatos() {
        return this.bd;
    }

    public Agente getAgente() {
        return this.agente;
    }

}
