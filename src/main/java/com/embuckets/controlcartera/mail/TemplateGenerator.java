/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.mail;

import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emilio
 */
public class TemplateGenerator {

    public static String getCobranzaMessage(NotificacionRecibo notificacionRecibo) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(Globals.TEMPLATE_COBRANZA_PATH));
            String template = new String(encoded, Charset.forName("utf-8"));
            template = template.replace("{asegurado.nombre}", notificacionRecibo.getNombreContratante());
            template = template.replace("{poliza.numero}", notificacionRecibo.getNumeroPoliza());

            template = template.replace("{recibo.inicio}", notificacionRecibo.getRecibo().getCubredesde().toString());
//            template = template.replace("{recibo.inicio}", DateTimeFormatter.ofPattern("dd-MM-YYYY").format(notificacionRecibo.getRecibo().getCubredesde()));
            template = template.replace("{recibo.total}", Globals.formatCantidad(notificacionRecibo.getRecibo().getImporte()));
            template = template.replace("{recibo.fin}", notificacionRecibo.getRecibo().getCubrehasta().toString());
//            template = template.replace("{plazo}", DateTimeFormatter.ofPattern("dd-MM-YYYY").format(recibo.getInicio().plusDays(20)));
            template = template.replace("{firma}", Agente.getInstance().getNombreCompleto());
            return template;
        } catch (IOException ex) {
            Logger.getLogger(TemplateGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String getCumpleMessage(NotificacionCumple notificacionCumple) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(Globals.TEMPLATE_CUMPLE_PATH));
            String template = new String(encoded, Charset.forName("utf-8"));
            template = template.replace("{asegurado.nombre}", notificacionCumple.getNombreAsegurado());
            return template;
        } catch (IOException ex) {
            Logger.getLogger(TemplateGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
