/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.mail;

import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class TemplateGenerator {

    private static final Logger logger = LogManager.getLogger(TemplateGenerator.class);

    public static String getCobranzaMessage(NotificacionRecibo notificacionRecibo) throws IOException {
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
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    public static String getCumpleMessage(NotificacionCumple notificacionCumple) throws IOException {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(Globals.TEMPLATE_CUMPLE_PATH));
            String template = new String(encoded, Charset.forName("utf-8"));
            template = template.replace("{asegurado.nombre}", notificacionCumple.getNombreAsegurado());
            return template;
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

}
