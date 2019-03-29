/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emilio
 */
public class Agente {

    private static Agente agente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String password;
    private Properties users;

    private Agente() {
        loadUserProperties();
    }

    private void loadUserProperties() {
        try (InputStream inDefaults = new FileInputStream("config/default.config");
                InputStream inUsers = new FileInputStream("config/user.config")) {
            Properties defaults = new Properties();
            defaults.load(inDefaults);
            users = new Properties(defaults);
            users.load(inUsers);
            this.nombre = users.getProperty("nombre");
            this.apellidoPaterno = users.getProperty("paterno");
            this.apellidoMaterno = users.getProperty("materno");
            this.email = users.getProperty("email");
            this.password = users.getProperty("password");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Agente getInstance() {
        if (agente == null) {
            agente = new Agente();
        }
        return agente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        users.setProperty("nombre", nombre);
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        users.put("paterno", apellidoPaterno);
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
        users.put("materno", apellidoMaterno);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        users.put("email", email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        users.put("password", password);
    }

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre);
        sb.append(" ");
        sb.append(apellidoPaterno);
        sb.append(" ");
        sb.append(apellidoMaterno);
        return sb.toString();
    }

    public void guardar() {
        try (OutputStream output = new FileOutputStream("config/user.config")) {
            users.store(output, "Actualizado: " + LocalDateTime.now());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
