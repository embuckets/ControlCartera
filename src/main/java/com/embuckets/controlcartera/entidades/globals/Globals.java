/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 *
 * @author emilio
 */
public class Globals {

    /**
     * dia de hoy menos 1 mes
     */
    public static LocalDate RECIBO_CUBRE_DESDE_INICIO_DEFAULT = LocalDate.now().minusMonths(1);

    /**
     * dia de hoy
     */
    public static LocalDate RECIBO_CUBRE_DESDE_FIN_DEFAULT = LocalDate.now();

    /**
     * dia de hoy
     */
    public static LocalDate RENOVACION_ENTRE_START_DEFAULT = LocalDate.now();

    /**
     * dia de hoy mas 1 mes
     */
    public static LocalDate RENOVACION_ENTRE_END_DEFAULT = LocalDate.now().plusMonths(1);

    /**
     * dia de hoy menos 7 dias
     */
    public static LocalDate CUMPLES_ENTRE_START_DEFAULT = LocalDate.now().minusDays(7);

    /**
     * dia de hoy menos 7 dias
     */
    public static LocalDate CUMPLES_ENTRE_END_DEFAULT = LocalDate.now().plusDays(7);

    /**
     * ruta a archivo de confuguracion por default
     */
    public static String DEFAULT_CONFIG_PATH = "config/default.config";

    /**
     * ruta a archivo de configuracion de usuario
     */
    public static String USER_CONFIG_PATH = "config/user.config";

    /**
     * ruta a archivo de servidores de correo
     */
    public static String SMTP_CONFIG_PATH = "config/smtp.config";

    /**
     * ruta a plantilla de correo de cumpleaños
     */
    public static String TEMPLATE_CUMPLE_PATH = "templates/cumple.html";

    /**
     * ruta a plantilla de correo de cobranza
     */
    public static String TEMPLATE_COBRANZA_PATH = "templates/cobranza.html";

    /**
     * ruta a plantila de excel
     */
    public static String TEMPLATE_EXCEL = "./templates/plantilla-cartera.xlsx";

    /**
     *
     */
    public static String DEFAULT_AGENTE_NOMBRE = "Su";

    /**
     *
     */
    public static String DEFAULT_AGENTE_PATERNO = "Asesor";

    /**
     *
     */
    public static String DEFAULT_AGENTE_MATERNO = "Financiero";

    /**
     *
     */
    public static String TIPO_PERSONA_MORAL = "Moral";

    /**
     *
     */
    public static String TIPO_PERSONA_FISICA = "Física";

    /**
     *
     */
    public static String TELEFONO_TIPO_CASA = "Casa";

    /**
     *
     */
    public static String TELEFONO_TIPO_MOVIL = "Móvil";

    /**
     *
     */
    public static String TELEFONO_TIPO_TRABAJO = "Trabajo";

    /**
     *
     */
    public static String EMAIL_TIPO_TRABAJO = "Trabajo";

    /**
     *
     */
    public static String EMAIL_TIPO_PERSONAL = "Personal";

    /**
     *
     */
    public static String MONEDA_PESOS = "Pesos";

    /**
     *
     */
    public static String MONEDA_DOLARES = "Dólares";

    /**
     *
     */
    public static String MONEDA_UMAM = "UMAM";

    /**
     *
     */
    public static String MONEDA_UDIS = "UDIS";

    /**
     *
     */
    public static String DOCUMENTO_ASEGURADO_TIPO_DOMICILIO = "Domicilio";

    /**
     *
     */
    public static String DOCUMENTO_ASEGURADO_TIPO_IDENTIFICACION = "Identificación";

    /**
     *
     */
    public static String DOCUMENTO_ASEGURADO_TIPO_RFC = "RFC";

    /**
     *
     */
    public static String DOCUMENTO_ASEGURADO_TIPO_OTRO = "Otro";

    /**
     *
     */
    public static String RECIBO_COBRANZA_PENDIENTE = "Pendiente";

    /**
     *
     */
    public static String RECIBO_COBRANZA_PAGADO = "Pagado";

    /**
     *
     */
    public static String CONDUCTO_COBRO_AGENTE = "Agente";

    /**
     *
     */
    public static String CONDUCTO_COBRO_CAT = "CAT";

    /**
     *
     */
    public static String CONDUCTO_COBRO_CASH = "CASH";

    /**
     *
     */
    public static String FORMA_PAGO_ANUAL = "Anual";

    /**
     *
     */
    public static String FORMA_PAGO_SEMESTRAL = "Semestral";

    /**
     *
     */
    public static String FORMA_PAGO_TRIMESTRAL = "Trimestral";

    /**
     *
     */
    public static String FORMA_PAGO_MENSUAL = "Mensual";

    /**
     *
     */
    public static String POLIZA_RAMO_AUTOS = "Autos";

    /**
     *
     */
    public static String POLIZA_RAMO_ACC_PER = "Accidentes personales";

    /**
     *
     */
    public static String POLIZA_RAMO_EMPRESARIAL = "Empresarial";

    /**
     *
     */
    public static String POLIZA_RAMO_GM = "Gastos médicos";

    /**
     *
     */
    public static String POLIZA_RAMO_HOGAR = "Hogar";

    /**
     *
     */
    public static String POLIZA_RAMO_INVERSION = "Inversión";

    /**
     *
     */
    public static String POLIZA_RAMO_RC = "Responsabilidad civil";

    /**
     *
     */
    public static String POLIZA_RAMO_TRANSPORTE = "Transporte";

    /**
     *
     */
    public static String POLIZA_RAMO_VIDA = "Vida";

    /**
     *
     */
    public static String POLIZA_RAMO_FLOTILLA = "Flotilla";

    /**
     *
     */
    public static String POLIZA_ESTADO_VIGENTE = "Vigente";

    /**
     *
     */
    public static String POLIZA_ESTADO_RENOVADA = "Renovada";

    /**
     *
     */
    public static String POLIZA_ESTADO_CANCELADA = "Cancelada";

    /**
     *
     */
    public static String NOTIFICACION_ESTADO_PENDIENTE = "Pendiente";

    /**
     *
     */
    public static String NOTIFICACION_ESTADO_ENVIADO = "Enviado";

    /**
     *
     */
    public static String POLIZA_AUTO_SUMA_FACTURA = "Factura";

    /**
     *
     */
    public static String POLIZA_AUTO_SUMA_COMERCIAL = "Comercial";

    /**
     *
     * @return
     */
    public static String[] getAllTipoPersona() {
        return new String[]{
            TIPO_PERSONA_MORAL,
            TIPO_PERSONA_FISICA
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllTelefonoTipos() {
        return new String[]{
            TELEFONO_TIPO_CASA,
            TELEFONO_TIPO_MOVIL,
            TELEFONO_TIPO_TRABAJO
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllEmailTipos() {
        return new String[]{
            EMAIL_TIPO_PERSONAL,
            EMAIL_TIPO_TRABAJO
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllMonedas() {
        return new String[]{
            MONEDA_PESOS,
            MONEDA_DOLARES,
            MONEDA_UMAM,
            MONEDA_UDIS
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllDocumentoAseguradoTipos() {
        return new String[]{
            DOCUMENTO_ASEGURADO_TIPO_IDENTIFICACION,
            DOCUMENTO_ASEGURADO_TIPO_DOMICILIO,
            DOCUMENTO_ASEGURADO_TIPO_RFC,
            DOCUMENTO_ASEGURADO_TIPO_OTRO
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllReciboCobranzas() {
        return new String[]{
            RECIBO_COBRANZA_PENDIENTE,
            RECIBO_COBRANZA_PAGADO
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllConductoCobro() {
        return new String[]{
            CONDUCTO_COBRO_AGENTE,
            CONDUCTO_COBRO_CAT,
            CONDUCTO_COBRO_CASH
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllFormaPago() {
        return new String[]{
            FORMA_PAGO_ANUAL,
            FORMA_PAGO_SEMESTRAL,
            FORMA_PAGO_TRIMESTRAL,
            FORMA_PAGO_MENSUAL
        };
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public static String[] getAllPolizaEstados() {
        return new String[]{
            POLIZA_ESTADO_VIGENTE,
            POLIZA_ESTADO_RENOVADA,
            POLIZA_ESTADO_CANCELADA
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllNotificacionEstados() {
        return new String[]{
            NOTIFICACION_ESTADO_PENDIENTE,
            NOTIFICACION_ESTADO_ENVIADO
        };
    }

    /**
     *
     * @return
     */
    public static String[] getAllPolizaAutoSumas() {
        return new String[]{
            POLIZA_AUTO_SUMA_FACTURA,
            POLIZA_AUTO_SUMA_COMERCIAL
        };
    }

    /**
     * Metodo para formatear cantidades agregando signo de pesos y separado por
     * comas. en caso de string lo pone en mayusculas
     *
     * @param cantidad numero o string a formatear
     * @return la cantidad formateada
     */
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
