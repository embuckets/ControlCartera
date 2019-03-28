/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 *
 * @author emilio
 */
public class Globals {

    public static LocalDate RECIBO_CUBRE_DESDE_INICIO_DEFAULT = LocalDate.now().minusMonths(1);
    public static LocalDate RECIBO_CUBRE_DESDE_FIN_DEFAULT = LocalDate.now();
    
    public static LocalDate RENOVACION_ENTRE_START_DEFAULT = LocalDate.now();
    public static LocalDate RENOVACION_ENTRE_END_DEFAULT = LocalDate.now().plusMonths(1);
    
    public static LocalDate CUMPLES_ENTRE_START_DEFAULT = LocalDate.now().minusDays(7);
    public static LocalDate CUMPLES_ENTRE_END_DEFAULT = LocalDate.now().plusDays(7);
    
    public static String DEFAULT_CONFIG_PATH = "config/default.config";
    public static String USER_CONFIG_PATH = "config/user.config";
    public static String SMTP_CONFIG_PATH = "config/smtp.config";
    public static String TEMPLATE_CUMPLE_PATH = "templates/cumple.html";
    public static String TEMPLATE_COBRANZA_PATH = "templates/cobranza.html";

    public static String DEFAULT_AGENTE_NOMBRE = "Su";
    public static String DEFAULT_AGENTE_PATERNO = "Asesor";
    public static String DEFAULT_AGENTE_MATERNO = "Financiero";
    
    public static int DEFAULT_COBRANZA_DENTRO_PRIMEROS_DIAS = 20;
    public static int DEFAULT_CUMPLE_DENTRO_PRIMEROS_DIAS = 10;

    public static String TIPO_PERSONA_MORAL = "Moral";
    public static String TIPO_PERSONA_FISICA = "Física";

    public static String TELEFONO_TIPO_CASA = "Casa";
    public static String TELEFONO_TIPO_MOVIL = "Móvil";
    public static String TELEFONO_TIPO_TRABAJO = "Trabajo";

    public static String EMAIL_TIPO_TRABAJO = "Trabajo";
    public static String EMAIL_TIPO_PERSONAL = "Personal";

    public static String MONEDA_PESOS = "Pesos";
    public static String MONEDA_DOLARES = "Dólares";
    public static String MONEDA_UMAM = "UMAM";
    public static String MONEDA_UDIS = "UDIS";

    public static String DOCUMENTO_ASEGURADO_TIPO_DOMICILIO = "Domicilio";
    public static String DOCUMENTO_ASEGURADO_TIPO_IDENTIFICACION = "Identificación";
    public static String DOCUMENTO_ASEGURADO_TIPO_RFC = "RFC";
    public static String DOCUMENTO_ASEGURADO_TIPO_OTRO = "Otro";

    public static String RECIBO_COBRANZA_PENDIENTE = "Pendiente";
    public static String RECIBO_COBRANZA_PAGADO = "Pagado";

    public static String CONDUCTO_COBRO_AGENTE = "Agente";
    public static String CONDUCTO_COBRO_CAT = "CAT";
    public static String CONDUCTO_COBRO_CASH = "CASH";

    public static String FORMA_PAGO_ANUAL = "Anual";
    public static String FORMA_PAGO_SEMESTRAL = "Semestral";
    public static String FORMA_PAGO_TRIMESTRAL = "Trimestral";
    public static String FORMA_PAGO_MENSUAL = "Mensual";

    public static String POLIZA_RAMO_AUTOS = "Autos";
    public static String POLIZA_RAMO_ACC_PER = "Accidentes personales";
    public static String POLIZA_RAMO_EMPRESARIAL = "Empresarial";
    public static String POLIZA_RAMO_GM = "Gastos médicos";
    public static String POLIZA_RAMO_HOGAR = "Hogar";
    public static String POLIZA_RAMO_INVERSION = "Inversión";
    public static String POLIZA_RAMO_RC = "Responsabilidad civil";
    public static String POLIZA_RAMO_TRANSPORTE = "Transporte";
    public static String POLIZA_RAMO_VIDA = "Vida";
    public static String POLIZA_RAMO_FLOTILLA = "Flotilla";

    public static String POLIZA_ESTADO_VIGENTE = "Vigente";
    public static String POLIZA_ESTADO_RENOVADA = "Renovada";
    public static String POLIZA_ESTADO_CANCELADA = "Cancelada";

    public static String NOTIFICACION_ESTADO_PENDIENTE = "Pendiente";
    public static String NOTIFICACION_ESTADO_ENVIADO = "Enviado";

    public static String POLIZA_AUTO_SUMA_FACTURA = "Factura";
    public static String POLIZA_AUTO_SUMA_COMERCIAL = "Comercial";

    public static String[] getAllTipoPersona() {
        return new String[]{
            TIPO_PERSONA_MORAL,
            TIPO_PERSONA_FISICA
        };
    }

    public static String[] getAllTelefonoTipos() {
        return new String[]{
            TELEFONO_TIPO_CASA,
            TELEFONO_TIPO_MOVIL,
            TELEFONO_TIPO_TRABAJO
        };
    }

    public static String[] getAllEmailTipos() {
        return new String[]{
            EMAIL_TIPO_PERSONAL,
            EMAIL_TIPO_TRABAJO
        };
    }

    public static String[] getAllMonedas() {
        return new String[]{
            MONEDA_PESOS,
            MONEDA_DOLARES,
            MONEDA_UMAM,
            MONEDA_UDIS
        };
    }

    public static String[] getAllDocumentoAseguradoTipos() {
        return new String[]{
            DOCUMENTO_ASEGURADO_TIPO_IDENTIFICACION,
            DOCUMENTO_ASEGURADO_TIPO_DOMICILIO,
            DOCUMENTO_ASEGURADO_TIPO_RFC,
            DOCUMENTO_ASEGURADO_TIPO_OTRO
        };
    }

    public static String[] getAllReciboCobranzas() {
        return new String[]{
            RECIBO_COBRANZA_PENDIENTE,
            RECIBO_COBRANZA_PAGADO
        };
    }

    public static String[] getAllConductoCobro() {
        return new String[]{
            CONDUCTO_COBRO_AGENTE,
            CONDUCTO_COBRO_CAT,
            CONDUCTO_COBRO_CASH
        };
    }

    public static String[] getAllFormaPago() {
        return new String[]{
            FORMA_PAGO_ANUAL,
            FORMA_PAGO_SEMESTRAL,
            FORMA_PAGO_TRIMESTRAL,
            FORMA_PAGO_MENSUAL
        };
    }

    public static String[] getAllRamos() {
        return new String[]{
            POLIZA_RAMO_AUTOS,
            POLIZA_RAMO_ACC_PER,
            POLIZA_RAMO_EMPRESARIAL,
            POLIZA_RAMO_GM,
            POLIZA_RAMO_HOGAR,
            POLIZA_RAMO_INVERSION,
            POLIZA_RAMO_RC,
            POLIZA_RAMO_TRANSPORTE,
            POLIZA_RAMO_VIDA,
            POLIZA_RAMO_FLOTILLA
        };
    }

    public static String[] getAllPolizaEstados() {
        return new String[]{
            POLIZA_ESTADO_VIGENTE,
            POLIZA_ESTADO_RENOVADA,
            POLIZA_ESTADO_CANCELADA
        };
    }

    public static String[] getAllNotificacionEstados() {
        return new String[]{
            NOTIFICACION_ESTADO_PENDIENTE,
            NOTIFICACION_ESTADO_ENVIADO
        };
    }

    public static String[] getAllPolizaAutoSumas() {
        return new String[]{
            POLIZA_AUTO_SUMA_FACTURA,
            POLIZA_AUTO_SUMA_COMERCIAL
        };
    }

    public static String formatCantidad(Object cantidad) {
        DecimalFormat formatter = new DecimalFormat("$###,###,###.##");
        if (cantidad instanceof String) {
            String cantidadString = (String) cantidad;
            //si es un digito
            try {
                Float cantidadFloat = Float.valueOf(cantidadString);
                return formatter.format(cantidadFloat);
            } catch (Exception e) {
                return cantidadString.toUpperCase();
            }
        } else {
            return formatter.format(cantidad);
        }
    }
}
