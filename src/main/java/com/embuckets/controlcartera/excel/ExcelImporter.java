/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.excel;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.SumaAseguradaAuto;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author emilio
 */
public class ExcelImporter {

    private final int NOMBRE = 0;
    private final int PATERNO = 1;
    private final int MATERNO = 2;
    private final int TIPO_PERSONA = 3;
    private final int NUM_POLIZA = 4;
    private final int ASEGURADORA = 5;
    private final int RAMO = 6;
    private final int INICIO_VIGENCIA = 7;
    private final int FORMA_PAGO = 8;
    private final int CONDUCTO_COBRO = 9;
    private final int PRIMA = 10;
    private final int MONEDA = 11;
    private final int RECIBOS_PAGADOS = 12;
    private final int IMPORTE_CON_DERECHO = 13;
    private final int IMPORTE_SIGUIENTE = 14;
    private final int GM_DEDUCIBLE = 15;
    private final int GM_DEDUCIBLE_MONEDA = 16;
    private final int GM_SUMA_ASEGURADA = 17;
    private final int GM_SUMA_ASEGURADA_MONDEDA = 18;
    private final int GM_COASEGURO = 19;
    private final int AUTOS_SUMA_ASEGURADA = 20;
    private final int VIDA_SUMA = 21;
    private final int VIDA_SUMA_MONEDA = 22;
//    private List<String> errores;
    private final char[] columnas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private final String ERROR_CELL_STRING = "Se espereba texto";
    private final String ERROR_CELL_NUMBER = "Se espereba número";
    private final String ERROR_CELL_DATE = "Se espereba fecha";

    public ExcelImporter() {
    }

    public List<Asegurado> importar(File file) throws IOException, Exception {
        try (InputStream in = new FileInputStream(file)) {
            List<String> errores = new ArrayList<>();
            Map<String, Asegurado> asegurados = new HashMap<>();
            XSSFWorkbook wb = new XSSFWorkbook(in);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator rowIterator = sheet.rowIterator();
            rowIterator.next();//skip first row because headings
            rowIterator.next();//skip second row because headings
            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();
                boolean anyError = false;
                String nombre = getCellContentsAsString(row, NOMBRE);
                if (nombre == null) {
                    logError(errores, row.getRowNum(), columnas[NOMBRE], ERROR_CELL_STRING);
                    anyError = true;
                }
                String paterno = getCellContentsAsString(row, PATERNO);
                if (paterno == null) {
                    paterno = "";
//                    logError(errores, row.getRowNum(), columnas[PATERNO], ERROR_CELL_STRING);
//                    anyError = true;
                }
                String materno = getCellContentsAsString(row, MATERNO);
                if (materno == null) {
                    materno = "";
//                    logError(errores, row.getRowNum(), columnas[MATERNO], ERROR_CELL_STRING);
//                    anyError = true;
                }
                String tipoPersona = getCellContentsAsString(row, TIPO_PERSONA);
                if (tipoPersona == null) {
                    logError(errores, row.getRowNum(), columnas[TIPO_PERSONA], ERROR_CELL_STRING);
                    anyError = true;
                }
                String numPoliza = getCellContentsAsString(row, NUM_POLIZA);
                if (tipoPersona == null) {
                    logError(errores, row.getRowNum(), columnas[TIPO_PERSONA], ERROR_CELL_STRING);
                    anyError = true;
                }
                String aseguradora = getCellContentsAsString(row, ASEGURADORA);
                if (aseguradora == null) {
                    logError(errores, row.getRowNum(), columnas[ASEGURADORA], ERROR_CELL_STRING);
                    anyError = true;
                }
                String ramo = getCellContentsAsString(row, RAMO);
                if (ramo == null) {
                    logError(errores, row.getRowNum(), columnas[RAMO], ERROR_CELL_STRING);
                    anyError = true;
                }
                LocalDate inicioVigencia = getCellContentsAsDate(row, INICIO_VIGENCIA);
                if (inicioVigencia == null) {
                    logError(errores, row.getRowNum(), columnas[INICIO_VIGENCIA], ERROR_CELL_DATE);
                    anyError = true;
                }
                String formaPago = getCellContentsAsString(row, FORMA_PAGO);
                if (formaPago == null) {
                    logError(errores, row.getRowNum(), columnas[FORMA_PAGO], ERROR_CELL_STRING);
                    anyError = true;
                }
                String conductoCobro = getCellContentsAsString(row, CONDUCTO_COBRO);
                if (conductoCobro == null) {
                    logError(errores, row.getRowNum(), columnas[CONDUCTO_COBRO], ERROR_CELL_STRING);
                    anyError = true;
                }
                double prima = getCellContentsAsDouble(row, PRIMA);//error
                if (prima == 0) {
                    logError(errores, row.getRowNum(), columnas[PRIMA], ERROR_CELL_NUMBER);
                    anyError = true;
                }
                String moneda = getCellContentsAsString(row, MONEDA);
                if (moneda == null) {
                    logError(errores, row.getRowNum(), columnas[MONEDA], ERROR_CELL_STRING);
                    anyError = true;
                }
                double recibosPagados = getCellContentsAsDouble(row, RECIBOS_PAGADOS);
//                if (recibosPagados == 0) {
//                    logError(errores, row.getRowNum(), columnas[RECIBOS_PAGADOS], ERROR_CELL_NUMBER);
//                    anyError = true;
//                }
                double importeConDerechoPoliza = getCellContentsAsDouble(row, IMPORTE_CON_DERECHO);
                if (importeConDerechoPoliza == 0) {
                    logError(errores, row.getRowNum(), columnas[IMPORTE_CON_DERECHO], ERROR_CELL_NUMBER);
                    anyError = true;
                }
                double importeSiguiente = getCellContentsAsDouble(row, IMPORTE_SIGUIENTE);
                if (formaPago != null && !formaPago.equalsIgnoreCase(Globals.FORMA_PAGO_ANUAL) && importeSiguiente == 0) {
                    logError(errores, row.getRowNum(), columnas[IMPORTE_SIGUIENTE], ERROR_CELL_NUMBER);
                    anyError = true;
                }
                double gmDeducible = getCellContentsAsDouble(row, GM_DEDUCIBLE);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_GM) && gmDeducible == 0) {
                    logError(errores, row.getRowNum(), columnas[GM_DEDUCIBLE], ERROR_CELL_NUMBER);
                    anyError = true;
                }
                String gmDeducibleMoneda = getCellContentsAsString(row, GM_DEDUCIBLE_MONEDA);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_GM) && gmDeducibleMoneda == null) {
                    logError(errores, row.getRowNum(), columnas[GM_DEDUCIBLE_MONEDA], ERROR_CELL_STRING);
                    anyError = true;
                }
                String gmSumaAsegurada = getCellContentsAsString(row, GM_SUMA_ASEGURADA);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_GM) && gmSumaAsegurada == null) {
                    logError(errores, row.getRowNum(), columnas[GM_SUMA_ASEGURADA], ERROR_CELL_STRING);
                    anyError = true;
                }
                String gmSumaAseguradaMoneda = getCellContentsAsString(row, GM_SUMA_ASEGURADA_MONDEDA);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_GM) && gmSumaAseguradaMoneda == null) {
                    logError(errores, row.getRowNum(), columnas[GM_SUMA_ASEGURADA_MONDEDA], ERROR_CELL_STRING);
                    anyError = true;
                }
                double gmCoaseguro = getCellContentsAsDouble(row, GM_COASEGURO);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_GM) && gmCoaseguro == 0) {
                    logError(errores, row.getRowNum(), columnas[GM_COASEGURO], ERROR_CELL_NUMBER);
                    anyError = true;
                }
                String autoSuma = getCellContentsAsString(row, AUTOS_SUMA_ASEGURADA);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_AUTOS) && autoSuma == null) {
                    logError(errores, row.getRowNum(), columnas[AUTOS_SUMA_ASEGURADA], ERROR_CELL_STRING);
                    anyError = true;
                }
                double vidaSuma = getCellContentsAsDouble(row, VIDA_SUMA);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_VIDA) && vidaSuma == 0) {
                    logError(errores, row.getRowNum(), columnas[VIDA_SUMA], ERROR_CELL_NUMBER);
                    anyError = true;
                }
                String vidaSumaMoneda = getCellContentsAsString(row, VIDA_SUMA_MONEDA);
                if (ramo != null && ramo.equalsIgnoreCase(Globals.POLIZA_RAMO_VIDA) && vidaSumaMoneda == null) {
                    logError(errores, row.getRowNum(), columnas[VIDA_SUMA_MONEDA], ERROR_CELL_STRING);
                    anyError = true;
                }
                if (!anyError) {
                    //si no hay errores crea al asegurado
                    Asegurado asegurado = new Asegurado(nombre, paterno, materno);
                    asegurado.setTipopersona(new TipoPersona(tipoPersona));
                    if (asegurados.containsKey(asegurado.getNombreCompleto())) {
                        asegurado = asegurados.get(asegurado.getNombreCompleto());
                    } else {
                        asegurados.put(asegurado.getNombreCompleto(), asegurado);
                    }
                    Poliza poliza = new Poliza();
                    poliza.setContratante(asegurado);
                    poliza.setTitular(asegurado.getCliente());
                    poliza.setEstado(new EstadoPoliza(Globals.POLIZA_ESTADO_VIGENTE));
                    poliza.setNumero(numPoliza);
                    poliza.setAseguradora(new Aseguradora(aseguradora));
                    poliza.setRamo(new Ramo(ramo));
                    poliza.setIniciovigencia(inicioVigencia);
                    poliza.setFormapago(new FormaPago(formaPago));
                    poliza.setConductocobro(new ConductoCobro(conductoCobro));
                    poliza.setPrima(BigDecimal.valueOf(prima));
                    poliza.setPrimamoneda(new Moneda(moneda));
                    poliza.generarRecibos((int) recibosPagados, BigDecimal.valueOf(importeConDerechoPoliza), BigDecimal.valueOf(importeSiguiente));

                    if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_GM)) {
                        poliza.getPolizaGmm().setCoaseguro((short) gmCoaseguro);
                        poliza.getPolizaGmm().setDeducible(BigDecimal.valueOf(gmDeducible));
                        poliza.getPolizaGmm().setDeduciblemoneda(new Moneda(gmDeducibleMoneda));
                        poliza.getPolizaGmm().setSumaasegurada(gmSumaAsegurada);
                        poliza.getPolizaGmm().setSumaaseguradamondeda(new Moneda(gmSumaAseguradaMoneda));
                    } else if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_VIDA)) {
                        poliza.getPolizaVida().setSumaasegurada(BigDecimal.valueOf(vidaSuma));
                        poliza.getPolizaVida().setSumaaseguradamoneda(new Moneda(vidaSumaMoneda));
                    } else if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_AUTOS)) {
                        poliza.getPolizaAuto().setSumaaseguradaauto(new SumaAseguradaAuto(autoSuma));
                    }
                    asegurado.getPolizaList().add(poliza);
                }//end if !anyError
            }//end rowIterator
            if (!errores.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                errores.stream().forEach(s -> sb.append(s).append('\n'));
                throw new Exception(sb.toString());
            }
            return asegurados.values().stream().collect(Collectors.toList());
        } catch (IOException ex) {
            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private void logError(List<String> errores, int row, char column, String message) {
        errores.add("Error = " + (row + 1) + "," + column + " - " + message);
    }

    private String getCellContentsAsString(XSSFRow row, int column) {
        XSSFCell cell = row.getCell(column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell != null) {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getRawValue();
            }
        }
        return null;
    }

    private double getCellContentsAsDouble(XSSFRow row, int column) {
        XSSFCell cell = row.getCell(column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                if (!DateUtil.isCellDateFormatted(cell)) {
                    return cell.getNumericCellValue();
                }
            }
        }
        return 0;
    }

    private LocalDate getCellContentsAsDate(XSSFRow row, int column) {
        XSSFCell cell = row.getCell(column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
            }
        }
        return null;
    }

    private boolean isDataValid(Object... data) {
        for (Object obj : data) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isDataValid(double... data) {
        for (double obj : data) {
            if (obj == 0) {
                return false;
            }
        }
        return true;
    }

//    public List<String> getErrores() {
//        return errores;
//    }
}
