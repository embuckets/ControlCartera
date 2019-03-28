/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  emilio
 * Created: Jan 19, 2019
 */

-- MySQL Script generated by MySQL Workbench
-- Sat Jan 19 12:19:56 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema cartera
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table ESTADO
-- -----------------------------------------------------
CREATE TABLE ESTADO (
  Estado VARCHAR(19) NOT NULL,
  PRIMARY KEY (Estado))
;


-- -----------------------------------------------------
-- Table DELEGACION
-- -----------------------------------------------------
CREATE TABLE DELEGACION (
  Delegacion VARCHAR(22) NOT NULL,
  PRIMARY KEY (Delegacion))
;


-- -----------------------------------------------------
-- Table DOMICILIO
-- -----------------------------------------------------
CREATE TABLE DOMICILIO (
  idDomicilio INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Calle VARCHAR(45) NOT NULL,
  Exterior VARCHAR(45) NOT NULL,
  Interior VARCHAR(45),
  CodigoPostal VARCHAR(5),
  Colonia VARCHAR(45),
  Delegacion VARCHAR(22),
  Estado VARCHAR(19),
  PRIMARY KEY (idDomicilio),
  CONSTRAINT fk_domicilio_Estado
    FOREIGN KEY (Estado)
    REFERENCES ESTADO (Estado),
  CONSTRAINT fk_domicilio_delegacion
    FOREIGN KEY (Delegacion)
    REFERENCES DELEGACION (Delegacion))
;

-- CREATE INDEX fk_domicilio_estado_idx ON DOMICILIO (Estado ASC) ;

-- CREATE INDEX fk_domicilio_delegacion_idx ON DOMICILIO (Delegacion ASC) ;


-- -----------------------------------------------------
-- Table CLIENTE
-- -----------------------------------------------------
CREATE TABLE CLIENTE (
  idCliente INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Nombre VARCHAR(45) NOT NULL,
  ApellidoPaterno VARCHAR(45),
  ApellidoMaterno VARCHAR(45),
  Nacimiento DATE,
  PRIMARY KEY (idCliente),
  CONSTRAINT unq_cliente UNIQUE (Nombre,ApellidoPaterno,ApellidoMaterno)
);


-- -----------------------------------------------------
-- Table TIPO_PERSONA
-- -----------------------------------------------------
CREATE TABLE TIPO_PERSONA (
  TipoPersona VARCHAR(6) NOT NULL,
  PRIMARY KEY (TipoPersona))
;


-- -----------------------------------------------------
-- Table ASEGURADO
-- -----------------------------------------------------
CREATE TABLE ASEGURADO (
  idCliente INT NOT NULL,
  TipoPersona VARCHAR(6),
  Rfc VARCHAR(13),
  idDomicilio INT,
  Nota VARCHAR(100),
  PRIMARY KEY (idCliente),
  CONSTRAINT fk_asegurado_idDomicilio
    FOREIGN KEY (idDomicilio)
    REFERENCES DOMICILIO (idDomicilio),
  CONSTRAINT fk_asegurado_cliente
    FOREIGN KEY (idCliente)
    REFERENCES CLIENTE (idCliente),
  CONSTRAINT fk_asegurado_tipoPersona
    FOREIGN KEY (TipoPersona)
    REFERENCES TIPO_PERSONA (TipoPersona))
;

-- CREATE INDEX fk_asegurado_idDomicilio_idx ON ASEGURADO (idDomicilio ASC) ;

-- CREATE INDEX fk_asegurad_tipoPersona_idx ON ASEGURADO (TipoPersona ASC) ;


-- -----------------------------------------------------
-- Table TIPO_TELEFONO
-- -----------------------------------------------------
CREATE TABLE TIPO_TELEFONO (
  TipoTelefono VARCHAR(7) NOT NULL,
  PRIMARY KEY (TipoTelefono))
;


-- -----------------------------------------------------
-- Table TELEFONO
-- -----------------------------------------------------
CREATE TABLE TELEFONO (
  idCliente INT NOT NULL,
  Telefono VARCHAR(13) NOT NULL,
  Extension VARCHAR(6),
  TipoTelefono VARCHAR(7),
  PRIMARY KEY (Telefono, idCliente),
  CONSTRAINT fk_telefono_asegurado
    FOREIGN KEY (idCliente)
    REFERENCES ASEGURADO (idCliente),
  CONSTRAINT fk_telefono_tipoTelefono
    FOREIGN KEY (TipoTelefono)
    REFERENCES TIPO_TELEFONO (TipoTelefono))
;
-- CREATE INDEX fk_telefono_idAsegurado_idx ON TELEFONO (idCliente ASC) ;

-- CREATE INDEX fk_telefono_tipoTelefono_idx ON TELEFONO (TipoTelefono ASC) ;


-- -----------------------------------------------------
-- Table TIPO_EMAIL
-- -----------------------------------------------------
CREATE TABLE TIPO_EMAIL (
  TipoEmail VARCHAR(8) NOT NULL,
  PRIMARY KEY (TipoEmail))
;


-- -----------------------------------------------------
-- Table EMAIL
-- -----------------------------------------------------
CREATE TABLE EMAIL (
  idCliente INT NOT NULL,
  Email VARCHAR(45) NOT NULL,
  TipoEmail VARCHAR(8),
  NOTIFICAR BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (Email, idCliente),
  CONSTRAINT fk_email_asegurado
    FOREIGN KEY (idCliente)
    REFERENCES ASEGURADO (idCliente),
  CONSTRAINT fk_email_tipoEmail
    FOREIGN KEY (TipoEmail)
    REFERENCES TIPO_EMAIL (TipoEmail))
;

-- CREATE INDEX fk_email_idAsegurado_idx ON EMAIL (idCliente ASC) ;

-- CREATE INDEX fk_email_tipoEmail_idx ON EMAIL (TipoEmail ASC) ;


-- -----------------------------------------------------
-- Table ASEGURADORA
-- -----------------------------------------------------
CREATE TABLE ASEGURADORA (
  Aseguradora VARCHAR(30) NOT NULL,
  PRIMARY KEY (Aseguradora))
;


-- -----------------------------------------------------
-- Table RAMO
-- -----------------------------------------------------
CREATE TABLE RAMO (
  Ramo VARCHAR(21) NOT NULL,
  PRIMARY KEY (Ramo))
;


-- -----------------------------------------------------
-- Table CONDUCTO_COBRO
-- -----------------------------------------------------
CREATE TABLE CONDUCTO_COBRO (
  ConductoCobro VARCHAR(6) NOT NULL,
  PRIMARY KEY (ConductoCobro))
;

-- -----------------------------------------------------
-- Table FORMA_PAGO
-- -----------------------------------------------------
CREATE TABLE FORMA_PAGO (
  FormaPago VARCHAR(10) NOT NULL,
  PRIMARY KEY (FormaPago))
;


-- -----------------------------------------------------
-- Table MONEDA
-- -----------------------------------------------------
CREATE TABLE MONEDA (
  Moneda VARCHAR(7) NOT NULL,
  PRIMARY KEY (Moneda))
;


-- -----------------------------------------------------
-- Table ESTADO_POLIZA
-- -----------------------------------------------------
CREATE TABLE ESTADO_POLIZA (
  Estado VARCHAR(10) NOT NULL,
  PRIMARY KEY (Estado))
;


-- -----------------------------------------------------
-- Table POLIZA
-- -----------------------------------------------------
CREATE TABLE POLIZA (
  idPoliza INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Numero VARCHAR(20) NOT NULL,
  Aseguradora VARCHAR(30) NOT NULL,
  Contratante INT NOT NULL,
  Titular INT NOT NULL,
  Ramo VARCHAR(21) NOT NULL,
  Producto VARCHAR(45),
  Plan VARCHAR(45),
  InicioVigencia DATE NOT NULL,
  FinVigencia DATE NOT NULL,
  ConductoCobro VARCHAR(6) NOT NULL,
  FormaPago VARCHAR(10) NOT NULL,
  Prima DECIMAL(9,2) NOT NULL,
  PrimaMoneda VARCHAR(7) NOT NULL,
  Nota VARCHAR(100),
  Estado VARCHAR(10) NOT NULL,
  PRIMARY KEY (idPoliza),
  CONSTRAINT fk_poliza_conductoCobro
    FOREIGN KEY (ConductoCobro)
    REFERENCES CONDUCTO_COBRO (ConductoCobro),
  CONSTRAINT fk_poliza_formaPago
    FOREIGN KEY (FormaPago)
    REFERENCES FORMA_PAGO (FormaPago),
  CONSTRAINT fk_poliza_ramo
    FOREIGN KEY (Ramo)
    REFERENCES RAMO (Ramo),
  CONSTRAINT fk_poliza_aseguradora
    FOREIGN KEY (Aseguradora)
    REFERENCES ASEGURADORA (Aseguradora),
  CONSTRAINT fk_polizaPrimaMoneda_moneda
    FOREIGN KEY (PrimaMoneda)
    REFERENCES MONEDA (Moneda),
  CONSTRAINT fk_poliza_contratante
    FOREIGN KEY (Contratante)
    REFERENCES ASEGURADO (idCliente),
  CONSTRAINT fk_poliza_titular
    FOREIGN KEY (Titular)
    REFERENCES CLIENTE (idCliente),
  CONSTRAINT fk_poliza_estadoPoliza
    FOREIGN KEY (Estado)
    REFERENCES ESTADO_POLIZA (Estado))
;

-- CREATE INDEX fk_poliza_conductoCobro_idx ON POLIZA (ConductoCobro ASC) ;
-- 
-- CREATE INDEX fk_poliza_formaPago_idx ON POLIZA (FormaPago ASC) ;
-- 
-- CREATE INDEX fk_poliza_ramo_idx ON POLIZA (Ramo ASC) ;
-- 
-- CREATE INDEX fk_poliza_aseguradora_idx ON POLIZA (Aseguradora ASC) ;
-- 
-- CREATE INDEX fk_polizaPrimaMoneda_moneda_idx ON POLIZA (PrimaMoneda ASC) ;
-- 
-- CREATE INDEX fk_poliza_contratante_idx ON POLIZA (Contratante ASC) ;
-- 
-- CREATE INDEX fk_poliza_titular_idx ON POLIZA (Titular ASC) ;
-- 
-- CREATE INDEX fk_poliza_estadoPoliza_idx ON POLIZA (Estado ASC) ;


-- -----------------------------------------------------
-- Table CONTRATANTE
-- -----------------------------------------------------
-- CREATE TABLE CONTRATANTE (
--   idCliente INT NOT NULL,
--   idPoliza INT NOT NULL,
--   PRIMARY KEY (idCliente, idPoliza),
--   CONSTRAINT fk_contratante_asegurado
--     FOREIGN KEY (idCliente)
--     REFERENCES ASEGURADO (idCliente),
--   CONSTRAINT fk_contratante_poliza
--     FOREIGN KEY (idPoliza)
--     REFERENCES POLIZA (idPoliza))
-- ;

-- CREATE INDEX fk_contratante_poliza_idx ON CONTRATANTE (idPoliza ASC) ;


-- -----------------------------------------------------
-- Table TITULAR
-- -----------------------------------------------------
-- CREATE TABLE TITULAR (
--   idCliente INT NOT NULL,
--   idPoliza INT NOT NULL,
--   PRIMARY KEY (idCliente, idPoliza),
--   CONSTRAINT fk_titular_asegurado
--     FOREIGN KEY (idCliente)
--     REFERENCES ASEGURADO (idCliente),
--   CONSTRAINT fk_titular_poliza
--     FOREIGN KEY (idPoliza)
--     REFERENCES POLIZA (idPoliza))
-- ;

-- CREATE INDEX fk_titular_poliza_idx ON TITULAR (idPoliza ASC) ;

-- -----------------------------------------------------
-- Table COBRANZA
-- -----------------------------------------------------
CREATE TABLE COBRANZA (
  Cobranza VARCHAR(9) NOT NULL,
  PRIMARY KEY (Cobranza))
;


-- -----------------------------------------------------
-- Table RECIBO
-- -----------------------------------------------------
CREATE TABLE RECIBO (
  idRecibo INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  idPoliza INT NOT NULL,
  cubreDesde DATE NOT NULL,
  cubreHasta DATE NOT NULL,
  Importe DECIMAL(9,2) NOT NULL,
  Cobranza VARCHAR(9) NOT NULL,
  PRIMARY KEY (idRecibo),
  CONSTRAINT fk_recibo_cobranza
    FOREIGN KEY (Cobranza)
    REFERENCES COBRANZA (Cobranza),
  CONSTRAINT fk_recibo_poliza
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA (idPoliza))
;
-- CREATE INDEX fk_recibo_cobranza_idx ON RECIBO (Cobranza ASC) ;
-- 
-- CREATE INDEX fk_recibo_poliza_idx ON RECIBO (idPoliza ASC) ;


-- -----------------------------------------------------
-- Table POLIZA_VIDA
-- -----------------------------------------------------
CREATE TABLE POLIZA_VIDA (
  idPoliza INT NOT NULL,
  SumaAsegurada DECIMAL(9,2) NOT NULL,
  SumaAseguradaMoneda VARCHAR(7) NOT NULL,
  PRIMARY KEY (idPoliza),
  CONSTRAINT fk_polizaVida_poliza
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA (idPoliza),
  CONSTRAINT fk_polizaVidaSumaMoneda_moneda
    FOREIGN KEY (SumaAseguradaMoneda)
    REFERENCES MONEDA (Moneda))
;

-- CREATE INDEX fk_polizaVidaSumaMoneda_moneda_idx ON POLIZA_VIDA (SumaAseguradaMoneda ASC) ;


-- -----------------------------------------------------
-- Table BENEFICIARIO
-- -----------------------------------------------------
CREATE TABLE BENEFICIARIO (
  idCliente INT NOT NULL,
  idPoliza INT NOT NULL,
  PRIMARY KEY (idCliente, idPoliza),
  CONSTRAINT fk_beneficiario_polizaVida
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA_VIDA (idPoliza),
  CONSTRAINT fk_beneficiario_cliente
    FOREIGN KEY (idCliente)
    REFERENCES CLIENTE (idCliente))
;
-- CREATE INDEX fk_beneficiario_polizaVida_idx ON BENEFICIARIO (idPoliza ASC) ;


-- -----------------------------------------------------
-- Table SUMA_ASEGURADA_AUTO
-- -----------------------------------------------------
CREATE TABLE SUMA_ASEGURADA_AUTO (
  SumaAseguradaAuto VARCHAR(9) NOT NULL,
  PRIMARY KEY (SumaAseguradaAuto))
;


-- -----------------------------------------------------
-- Table POLIZA_AUTO
-- -----------------------------------------------------
CREATE TABLE POLIZA_AUTO (
  idPoliza INT NOT NULL,
  SumaAseguradaAuto VARCHAR(9) NOT NULL,
  PRIMARY KEY (idPoliza),
  CONSTRAINT fk_polizaAuto_poliza
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA (idPoliza),
  CONSTRAINT fk_polizaAutoSuma_sumaAuto
    FOREIGN KEY (SumaAseguradaAuto)
    REFERENCES SUMA_ASEGURADA_AUTO (SumaAseguradaAuto))
;

-- CREATE INDEX fk_polizaAutoSuma_sumaAuto_idx ON POLIZA_AUTO (SumaAseguradaAuto ASC) ;


-- -----------------------------------------------------
-- Table AUTO
-- -----------------------------------------------------
CREATE TABLE AUTO (
  idAuto INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  idPoliza INT NOT NULL,
  Descripcion VARCHAR(30) NOT NULL,
  Marca VARCHAR(20) NOT NULL,
  Submarca VARCHAR(20) NOT NULL,
  Modelo DATE NOT NULL,
  PRIMARY KEY (idAuto),
  CONSTRAINT fk_auto_polizaAuto
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA_AUTO (idPoliza))
;


-- -----------------------------------------------------
-- Table POLIZA_GMM
-- -----------------------------------------------------
CREATE TABLE POLIZA_GMM (
  idPoliza INT NOT NULL,
  Deducible DECIMAL(9,2) NOT NULL,
  DeducibleMoneda VARCHAR(7) NOT NULL,
  SumaAsegurada VARCHAR(14) NOT NULL,
  SumaAseguradaMondeda VARCHAR(7) NOT NULL,
  Coaseguro SMALLINT NOT NULL,
  PRIMARY KEY (idPoliza),
  CONSTRAINT fk_polizagmm_poliza
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA (idPoliza),
  CONSTRAINT fk_polizaGmmDedMoneda_moneda
    FOREIGN KEY (DeducibleMoneda)
    REFERENCES MONEDA (Moneda) ,
  CONSTRAINT fk_polizaGmmSumMoneda_moneda
    FOREIGN KEY (SumaAseguradaMondeda)
    REFERENCES MONEDA (Moneda))
;

-- CREATE INDEX fk_polizaGmmDedMoneda_moneda_idx ON POLIZA_GMM (DeducibleMoneda ASC) ;
-- 
-- CREATE INDEX fk_polizaGmmSumMoneda_moneda_idx ON POLIZA_GMM (SumaAseguradaMondeda ASC) ;


-- -----------------------------------------------------
-- Table DEPENDIENTE
-- -----------------------------------------------------
CREATE TABLE DEPENDIENTE (
  idCliente INT NOT NULL,
  idPoliza INT NOT NULL,
  PRIMARY KEY (idCliente, idPoliza),
  CONSTRAINT fk_dependiente_polizagmm
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA_GMM (idPoliza),
  CONSTRAINT fk_dependiente_cliente
    FOREIGN KEY (idCliente)
    REFERENCES CLIENTE (idCliente))
;

-- CREATE INDEX fk_dependiente_polizagmm_idx ON DEPENDIENTE (idPoliza ASC) ;


-- -----------------------------------------------------
-- Table TIPO_DOCUMENTO_ASEGURADO
-- -----------------------------------------------------
CREATE TABLE TIPO_DOCUMENTO_ASEGURADO (
  TipoDocumento VARCHAR(14) NOT NULL,
  PRIMARY KEY (TipoDocumento))
;


-- -----------------------------------------------------
-- Table DOCUMENTO_ASEGURADO
-- -----------------------------------------------------
CREATE TABLE DOCUMENTO_ASEGURADO (
  idCliente INT NOT NULL,
  Nombre VARCHAR(45) NOT NULL,
  Extension VARCHAR(6) NOT NULL,
  Archivo BLOB (7M) NOT NULL,
  TipoDocumento VARCHAR(14) NOT NULL,
  Actualizado TIMESTAMP WITH DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idCliente, Nombre, TipoDocumento),
  CONSTRAINT fk_documentoAsegurado_asegurado
    FOREIGN KEY (idCliente)
    REFERENCES ASEGURADO (idCliente),
  CONSTRAINT fk_documentoAsegurado_tipoDocumentoasegurado
    FOREIGN KEY (TipoDocumento)
    REFERENCES TIPO_DOCUMENTO_ASEGURADO (TipoDocumento))
;

-- CREATE INDEX fk_documentoAsegurado_tipoDocumentoasegurado_idx ON DOCUMENTO_ASEGURADO (TipoDocumento ASC) ;


-- -----------------------------------------------------
-- Table CARATULA
-- -----------------------------------------------------
CREATE TABLE CARATULA (
  idPoliza INT NOT NULL,
  Nombre VARCHAR(45) NOT NULL,
  Extension VARCHAR(6) NOT NULL,
  Archivo BLOB (20M) NOT NULL,
  Actualizado TIMESTAMP WITH DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idPoliza),
  CONSTRAINT fk_caratula_poliza
    FOREIGN KEY (idPoliza)
    REFERENCES POLIZA (idPoliza))
;


-- -----------------------------------------------------
-- Table DOCUMENTO_RECIBO
-- -----------------------------------------------------
CREATE TABLE DOCUMENTO_RECIBO (
  idRecibo INT NOT NULL,
  Nombre VARCHAR(45) NOT NULL,
  Extension VARCHAR(6) NOT NULL,
  Archivo BLOB (2M) NOT NULL,
  Actualizado TIMESTAMP WITH DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idRecibo),
  CONSTRAINT fk_documentoRecibo_recibo
    FOREIGN KEY (idRecibo)
    REFERENCES RECIBO (idRecibo))
;


-- -----------------------------------------------------
-- Table ESTADO_NOTIFICACION
-- -----------------------------------------------------
CREATE TABLE  ESTADO_NOTIFICACION (
  EstadoNotificacion VARCHAR(9) NOT NULL,
  PRIMARY KEY (EstadoNotificacion))
;

-- -----------------------------------------------------
-- Table NOTIFICACION_CUMPLE
-- -----------------------------------------------------
CREATE TABLE  NOTIFICACION_CUMPLE (
  idCliente INT NOT NULL,
  Enviado TIMESTAMP,--  WITH DEFAULT CURRENT_TIMESTAMP
  EstadoNotificacion VARCHAR(9) NOT NULL,
  PRIMARY KEY (idCliente),
  CONSTRAINT fk_notifCumple_estadoNotif
    FOREIGN KEY (EstadoNotificacion)
    REFERENCES ESTADO_NOTIFICACION (EstadoNotificacion),
  CONSTRAINT fk_notifCumple_cliente
    FOREIGN KEY (idCliente)
    REFERENCES CLIENTE (idCliente))
;


-- -----------------------------------------------------
-- Table NOTIFICACION_RECIBO
-- -----------------------------------------------------
CREATE TABLE  NOTIFICACION_RECIBO (
  idRecibo INT NOT NULL,
  Enviado TIMESTAMP,--  WITH DEFAULT CURRENT_TIMESTAMP
  EstadoNotificacion VARCHAR(9) NOT NULL,
  PRIMARY KEY (idRecibo),
  CONSTRAINT fk_notifRecibo_estadoNotif
    FOREIGN KEY (EstadoNotificacion)
    REFERENCES ESTADO_NOTIFICACION (EstadoNotificacion),
  CONSTRAINT fk_notifRecibo_recibo
    FOREIGN KEY (idRecibo)
    REFERENCES RECIBO (idRecibo))
;


-- -----------------------------------------------------
-- Data for table ESTADO
-- -----------------------------------------------------

INSERT INTO ESTADO (Estado) VALUES ('Aguascalientes');
INSERT INTO ESTADO (Estado) VALUES ('Baja California');
INSERT INTO ESTADO (Estado) VALUES ('Baja California Sur');
INSERT INTO ESTADO (Estado) VALUES ('Campeche');
INSERT INTO ESTADO (Estado) VALUES ('Chiapas');
INSERT INTO ESTADO (Estado) VALUES ('Chihuahua');
INSERT INTO ESTADO (Estado) VALUES ('Ciudad de México');
INSERT INTO ESTADO (Estado) VALUES ('Coahuila');
INSERT INTO ESTADO (Estado) VALUES ('Colima');
INSERT INTO ESTADO (Estado) VALUES ('Durango');
INSERT INTO ESTADO (Estado) VALUES ('Estado de México');
INSERT INTO ESTADO (Estado) VALUES ('Guanajuato');
INSERT INTO ESTADO (Estado) VALUES ('Guerrero');
INSERT INTO ESTADO (Estado) VALUES ('Hidalgo');
INSERT INTO ESTADO (Estado) VALUES ('Jalisco');
INSERT INTO ESTADO (Estado) VALUES ('Michoacán');
INSERT INTO ESTADO (Estado) VALUES ('Morelos');
INSERT INTO ESTADO (Estado) VALUES ('Nayarit');
INSERT INTO ESTADO (Estado) VALUES ('Nuevo León');
INSERT INTO ESTADO (Estado) VALUES ('Oaxaca');
INSERT INTO ESTADO (Estado) VALUES ('Puebla');
INSERT INTO ESTADO (Estado) VALUES ('Querétaro');
INSERT INTO ESTADO (Estado) VALUES ('Quintana Roo');
INSERT INTO ESTADO (Estado) VALUES ('San Luis Potosí');
INSERT INTO ESTADO (Estado) VALUES ('Sinaloa');
INSERT INTO ESTADO (Estado) VALUES ('Sonora');
INSERT INTO ESTADO (Estado) VALUES ('Tabasco');
INSERT INTO ESTADO (Estado) VALUES ('Tamaulipas');
INSERT INTO ESTADO (Estado) VALUES ('Tlaxcala');
INSERT INTO ESTADO (Estado) VALUES ('Veracruz');
INSERT INTO ESTADO (Estado) VALUES ('Yucatán');
INSERT INTO ESTADO (Estado) VALUES ('Zacatecas');




-- -----------------------------------------------------
-- Data for table DELEGACION
-- -----------------------------------------------------

INSERT INTO DELEGACION (Delegacion) VALUES ('Álvaro Obregón');
INSERT INTO DELEGACION (Delegacion) VALUES ('Azcapotzalco');
INSERT INTO DELEGACION (Delegacion) VALUES ('Benito Juárez');
INSERT INTO DELEGACION (Delegacion) VALUES ('Coyoacán');
INSERT INTO DELEGACION (Delegacion) VALUES ('Cuajimalpa de Morelos');
INSERT INTO DELEGACION (Delegacion) VALUES ('Cuauhtémoc');
INSERT INTO DELEGACION (Delegacion) VALUES ('Gustavo A. Madero');
INSERT INTO DELEGACION (Delegacion) VALUES ('Iztacalco');
INSERT INTO DELEGACION (Delegacion) VALUES ('Iztapalapa');
INSERT INTO DELEGACION (Delegacion) VALUES ('La Magdalena Contreras');
INSERT INTO DELEGACION (Delegacion) VALUES ('Miguel Hidalgo');
INSERT INTO DELEGACION (Delegacion) VALUES ('Milpa Alta');
INSERT INTO DELEGACION (Delegacion) VALUES ('Tláhuac');
INSERT INTO DELEGACION (Delegacion) VALUES ('Tlalpan');
INSERT INTO DELEGACION (Delegacion) VALUES ('Venustiano Carranza');
INSERT INTO DELEGACION (Delegacion) VALUES ('Xochimilco');




-- -----------------------------------------------------
-- Data for table TIPO_PERSONA
-- -----------------------------------------------------

INSERT INTO TIPO_PERSONA (TipoPersona) VALUES ('Física');
INSERT INTO TIPO_PERSONA (TipoPersona) VALUES ('Moral');




-- -----------------------------------------------------
-- Data for table TIPO_TELEFONO
-- -----------------------------------------------------

INSERT INTO TIPO_TELEFONO (TipoTelefono) VALUES ('Casa');
INSERT INTO TIPO_TELEFONO (TipoTelefono) VALUES ('Móvil');
INSERT INTO TIPO_TELEFONO (TipoTelefono) VALUES ('Trabajo');




-- -----------------------------------------------------
-- Data for table TIPO_EMAIL
-- -----------------------------------------------------

INSERT INTO TIPO_EMAIL (TipoEmail) VALUES ('Personal');
INSERT INTO TIPO_EMAIL (TipoEmail) VALUES ('Trabajo');




-- -----------------------------------------------------
-- Data for table ASEGURADORA
-- -----------------------------------------------------

INSERT INTO ASEGURADORA (Aseguradora) VALUES ('A.N.A.');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('AIG');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Allianz');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Armour Secure Insurance');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Aserta');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Insurgentes');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Assurant');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Atradius');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('AXA');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Berkley International');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('BUPA');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Cardif');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Chubb');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Citibanamex');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('El Águila');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('GNP');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('HDI');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('HDI-Gerling');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('HSBC');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Insignia Life');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('La Latinoamericana Seguros');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Mapfre');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('MetLife');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Old Mutual Life');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Pan-American');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Plan Seguro');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Inbursa');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Quálitas');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Afirme');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Argos');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Atlas');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Azteca');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Banorte');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('BBVA Bancomer');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Sura');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Monterrey New York Life');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Tokio Marine');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('UMBRELLA');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Virginia Surety');
INSERT INTO ASEGURADORA (Aseguradora) VALUES ('Zurich');




-- -----------------------------------------------------
-- Data for table RAMO
-- -----------------------------------------------------

INSERT INTO RAMO (Ramo) VALUES ('Autos');
INSERT INTO RAMO (Ramo) VALUES ('Accidentes personales');
INSERT INTO RAMO (Ramo) VALUES ('Empresarial');
INSERT INTO RAMO (Ramo) VALUES ('Flotilla');
INSERT INTO RAMO (Ramo) VALUES ('Gastos médicos');
INSERT INTO RAMO (Ramo) VALUES ('Hogar');
INSERT INTO RAMO (Ramo) VALUES ('Inversión');
INSERT INTO RAMO (Ramo) VALUES ('Responsabilidad civil');
INSERT INTO RAMO (Ramo) VALUES ('Transporte');
INSERT INTO RAMO (Ramo) VALUES ('Vida');




-- -----------------------------------------------------
-- Data for table CONDUCTO_COBRO
-- -----------------------------------------------------

INSERT INTO CONDUCTO_COBRO (ConductoCobro) VALUES ('Agente');
INSERT INTO CONDUCTO_COBRO (ConductoCobro) VALUES ('CAT');
INSERT INTO CONDUCTO_COBRO (ConductoCobro) VALUES ('Cash');




-- -----------------------------------------------------
-- Data for table FORMA_PAGO
-- -----------------------------------------------------

INSERT INTO FORMA_PAGO (FormaPago) VALUES ('Anual');
INSERT INTO FORMA_PAGO (FormaPago) VALUES ('Semestral');
INSERT INTO FORMA_PAGO (FormaPago) VALUES ('Trimestral');
INSERT INTO FORMA_PAGO (FormaPago) VALUES ('Mensual');




-- -----------------------------------------------------
-- Data for table MONEDA
-- -----------------------------------------------------

INSERT INTO MONEDA (Moneda) VALUES ('Pesos');
INSERT INTO MONEDA (Moneda) VALUES ('Dólares');
INSERT INTO MONEDA (Moneda) VALUES ('UMAM');
INSERT INTO MONEDA (Moneda) VALUES ('UDIS');




-- -----------------------------------------------------
-- Data for table ESTADO_POLIZA
-- -----------------------------------------------------

INSERT INTO ESTADO_POLIZA (Estado) VALUES ('Vigente');
INSERT INTO ESTADO_POLIZA (Estado) VALUES ('Renovada');
INSERT INTO ESTADO_POLIZA (Estado) VALUES ('Cancelada');




-- -----------------------------------------------------
-- Data for table COBRANZA
-- -----------------------------------------------------

INSERT INTO COBRANZA (Cobranza) VALUES ('Pendiente');
INSERT INTO COBRANZA (Cobranza) VALUES ('Pagado');




-- -----------------------------------------------------
-- Data for table SUMA_ASEGURADA_AUTO
-- -----------------------------------------------------

INSERT INTO SUMA_ASEGURADA_AUTO (SumaAseguradaAuto) VALUES ('Factura');
INSERT INTO SUMA_ASEGURADA_AUTO (SumaAseguradaAuto) VALUES ('Comercial');




-- -----------------------------------------------------
-- Data for table TIPO_DOCUMENTO_ASEGURADO
-- -----------------------------------------------------

INSERT INTO TIPO_DOCUMENTO_ASEGURADO (TipoDocumento) VALUES ('Domicilio');
INSERT INTO TIPO_DOCUMENTO_ASEGURADO (TipoDocumento) VALUES ('Identificación');
INSERT INTO TIPO_DOCUMENTO_ASEGURADO (TipoDocumento) VALUES ('RFC');
INSERT INTO TIPO_DOCUMENTO_ASEGURADO (TipoDocumento) VALUES ('Otro');

-- -----------------------------------------------------
-- Data for table ESTADO_NOTIFICACION
-- -----------------------------------------------------
INSERT INTO ESTADO_NOTIFICACION (EstadoNotificacion) VALUES ('Pendiente');
INSERT INTO ESTADO_NOTIFICACION (EstadoNotificacion) VALUES ('Enviado');
