
INSERT INTO APP.CLIENTE (NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO, NACIMIENTO) 
	VALUES ('TEST', 'TEST', 'TEST2', '2019-03-11');

INSERT INTO APP.ASEGURADO (IDCLIENTE, TIPOPERSONA, RFC, IDDOMICILIO, NOTA) 
	VALUES (IDENTITY_VAL_LOCAL(), 'Fisica', 'RFC', NULL, NULL);

INSERT INTO APP.POLIZA (NUMERO, ASEGURADORA, CONTRATANTE, TITULAR, RAMO, PRODUCTO, PLAN, INICIOVIGENCIA, FINVIGENCIA, CONDUCTOCOBRO, FORMAPAGO, PRIMA, PRIMAMONEDA, NOTA, ESTADO) 
	VALUES ('123456', 'GNP', IDENTITY_VAL_LOCAL(), IDENTITY_VAL_LOCAL(), 'Autos', 'prod', 'plan', '2019-03-11', '2020-03-11', 'Agente', 'Anual', 1.00, 'Pesos', '', 'Vigente');


INSERT INTO APP.POLIZA_AUTO (IDPOLIZA, SUMAASEGURADAAUTO) 
	VALUES (IDENTITY_VAL_LOCAL(), 'Comercial');

INSERT INTO APP.AUTO (IDPOLIZA, DESCRIPCION, MARCA, SUBMARCA, MODELO) 
	VALUES (IDENTITY_VAL_LOCAL(), 'DESC', 'MARCA', 'SUB', '2019-03-11');

values IDENTITY_VAL_LOCAL();