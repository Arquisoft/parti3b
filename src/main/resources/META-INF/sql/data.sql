INSERT INTO TADMINISTRADOR(ID,PASSWORD,USUARIO) VALUES (1,'admin','admin')
INSERT INTO TADMINISTRADOR(ID,PASSWORD,USUARIO) VALUES (2,'admin2','admin2')
INSERT INTO TADMINISTRADOR(ID,PASSWORD,USUARIO) VALUES (3,'admin3','admin3')
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (1,'71435345N','Prada','direccion','seila@gmail.com','1996-01-15','espa','Seila','llFh9oTmjUI=','Seila_seila');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (2,'72344554T','Prada','direccion2','seila2@gmail.com','1996-01-15','espa','Seila2','llFh9oTmjUI=','Seila2_seila');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (3,'74564534E','Prada','direccion3','seila3@gmail.com','1996-01-15','espa','Seila3','llFh9oTmjUI=','Seila3_seila');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (4,'45654665K','Watson','direccion4','david@gmail.com','1986-04-19','eng','David','llFh9oTmjUI=','David_david');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (5,'45654465V','Rodriguez','direccion5','borja@gmail.com','1995-07-28','espa','Borja','llFh9oTmjUI=','Borja_borja');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (6,'56767567Q','Fernandez','direccion6','ruben@gmail.com','1993-03-02','espa','Ruben','llFh9oTmjUI=','Ruben_ruben');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (7,'38769345L','Ramon','direccion7','juan@gmail.com','1994-08-21','espa','Juan','llFh9oTmjUI=','Juan_juan');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (8,'67569789I','Garcia','direccion8','laura@gmail.com','1991-12-10','espa','Laura','llFh9oTmjUI=','Laura_laura');
INSERT INTO TCITIZENS(ID,DNI,APELLIDOS,DIRECCION,EMAIL,FECHANACIMIENTO,NACIONALIDAD,NOMBRE,PASSWORD,USUARIO) VALUES (9,'67569789Z','Garcia','direccion8','laura@gmail.es','1991-12-10','espa','Laura','user1','user1');
INSERT INTO TCATEGORIA(ID,FECHAFIN,FECHAINICIO,MINIMOVOTOS,NOMBRE) VALUES (1,'2000-01-01','2050-01-01','5','CategoriaDefecto')
INSERT INTO TCATEGORIA(ID,FECHAFIN,FECHAINICIO,MINIMOVOTOS,NOMBRE) VALUES (2,'2017-10-04','2017-01-04','5','Categoria1')
INSERT INTO TCATEGORIA(ID,FECHAFIN,FECHAINICIO,MINIMOVOTOS,NOMBRE) VALUES (3,'2017-10-12','2017-01-01','2','Categoria2')
INSERT INTO TCATEGORIA(ID,FECHAFIN,FECHAINICIO,MINIMOVOTOS,NOMBRE) VALUES (4,'2017-10-04','2017-01-04','3','Categoria3')
INSERT INTO TCATEGORIA(ID,FECHAFIN,FECHAINICIO,MINIMOVOTOS,NOMBRE) VALUES (5,'2017-10-03','2017-01-02','5','Categoria4')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (2,'prueba')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (2,'prueba2')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (2,'prueba3')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (3,'prueba')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (3,'prueba2')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (3,'prueba3')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (4,'prueba')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (4,'prueba2')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (4,'prueba3')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (5,'prueba')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (5,'prueba2')
INSERT INTO TPALABRAS(CATEGORIA_ID,PALABRA) VALUES (5,'prueba3')
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (1,'contenido de la primera sugerencia','Aceptada','Sugerencia1',5,9)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (2,'contenido de la segunda sugerencia','EnVotacion','Sugerencia2',5,2)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (3,'contenido de la tercera sugerencia','EnVotacion','Sugerencia3',5,4)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (4,'contenido de la cuarta sugerencia','EnVotacion','Sugerencia4',5,8)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (5,'contenido de la quinta sugerencia','EnVotacion','Sugerencia5',2,1)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (6,'contenido de la sexta sugerencia','EnVotacion','Sugerencia6',2,3)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (7,'contenido de la septima sugerencia','Anulada','Sugerencia7',2,3)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (8,'contenido de la octava sugerencia','EnVotacion','Sugerencia8',2,1)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (9,'contenido de la novena sugerencia','EnVotacion','Sugerencia9',3,2)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (10,'contenido de la decima sugerencia','EnVotacion','Sugerencia10',3,5)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (11,'contenido de la undecima sugerencia','EnVotacion','Sugerencia11',3,6)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (12,'contenido de la duodecima sugerencia','EnVotacion','Sugerencia12',3,7)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (13,'contenido de la decimotercera sugerencia','EnVotacion','Sugerencia13',5,1)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (14,'contenido de la decimocuarta sugerencia','EnVotacion','Sugerencia14',5,2)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (15,'contenido de la decimoquinta sugerencia','EnVotacion','Sugerencia15',4,5)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (16,'contenido de la decimosexta sugerencia','EnVotacion','Sugerencia16',4,4)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (17,'contenido de la decimoseptima sugerencia','Anulada','Sugerencia17',4,8)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (18,'contenido de la decimooctava sugerencia','Aceptada','Sugerencia18',4,2)
INSERT INTO TSUGERENCIA(ID,CONTENIDO,ESTADO,TITULO,CATEGORIA_ID,CITIZEN_ID) VALUES (19,'contenido de la decimonovena sugerencia','Aceptada','Sugerencia19',4,7)
INSERT INTO TCOMENTARIO(ID,CONTENIDO,CITIZEN_ID,SUGERENCIA_ID) VALUES (1,'contenido primer comentario',1,1)
INSERT INTO TCOMENTARIO(ID,CONTENIDO,CITIZEN_ID,SUGERENCIA_ID) VALUES (2,'contenido segundo comentario',2,1)
INSERT INTO TCOMENTARIO(ID,CONTENIDO,CITIZEN_ID,SUGERENCIA_ID) VALUES (3,'contenido tercer comentario',1,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,1,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,2,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (FALSE,3,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,4,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,5,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,6,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,7,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,8,1)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,1,2)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,2,2)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,3,2)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,4,2)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,1,3)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,2,3)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (FALSE,3,3)
INSERT INTO TVOTOCOMENTARIO(ISAFAVOR,CITIZEN_ID,COMENTARIO_ID) VALUES (TRUE,4,3)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,1,1)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,2,1)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (FALSE,3,1)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,4,1)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,1,2)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,2,2)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,3,2)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,4,2)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,1,3)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,2,3)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (FALSE,3,3)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,4,3)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,1,18)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,2,18)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (FALSE,3,18)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,4,18)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,7,18)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,8,18)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,1,19)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,2,19)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (FALSE,3,19)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,4,19)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,7,19)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,8,19)
INSERT INTO TVOTOSUGERENCIA(ISAFAVOR,CITIZEN_ID,SUGERENCIA_ID) VALUES (TRUE,5,19)