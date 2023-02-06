DROP DATABASE IF EXISTS eleccions2019;
    
CREATE DATABASE eleccions2019;
USE eleccions2019;
   
   CREATE TABLE comunitats_autonomes(
   comunitat_aut_id TINYINT  AUTO_INCREMENT,
   nom 				VARCHAR(45),
   codi_ine 		CHAR(2) NOT NULL,
   CONSTRAINT pk_comunitats_autonomes PRIMARY KEY (comunitat_aut_id)
   );
   
   CREATE TABLE provincies (
   provincia_id 	TINYINT AUTO_INCREMENT,
   comunitat_aut_id TINYINT,
   nom 				VARCHAR (45),
   codi_ine 		CHAR(2)NOT NULL,
   num_escons 		TINYINT,
   CONSTRAINT pk_provincies PRIMARY KEY (provincia_id)
   );
  
  CREATE TABLE municipis (
   municipi_id 	SMALLINT AUTO_INCREMENT,
   nom 			VARCHAR(100),
   codi_ine 	CHAR(3) NOT NULL,
   provincia_id TINYINT,
   districte    CHAR(2),
   CONSTRAINT pk_municipis PRIMARY KEY (municipi_id)
);   

	CREATE TABLE eleccions(
	eleccio_id		TINYINT AUTO_INCREMENT,
	nom 			VARCHAR	(45),
	data			DATE NOT NULL,
	any				YEAR NOT NULL,
	mes				TINYINT NOT NULL,
	CONSTRAINT pk_eleccions PRIMARY KEY (eleccio_id)
);

	CREATE TABLE eleccions_municipis(
	eleccio_id			TINYINT,
	municipi_id 		SMALLINT,
	num_meses       	SMALLINT,
	cens            	INT,
	vots_emesos			INT,
	vots_valis			INT,
	vots_candidatures	INT,
	vots_blancs         INT,
	vots_nuls           INT
);

	CREATE TABLE persones (
	persona_id		INT AUTO_INCREMENT,
	nom 			VARCHAR(30),
	cog1	 		VARCHAR(30),
	cog2	 		VARCHAR(30),
	sexe        	ENUM('M','F'),
	data_naixement  DATE,
	dni             CHAR(10),
	CONSTRAINT pk_persones PRIMARY KEY (persona_id)
);

	CREATE TABLE candidatures(
	candidatura_id      		INT AUTO_INCREMENT,
	eleccio_id          		TINYINT,
	codi_candidatura    		CHAR(6),
	nom_curt					VARCHAR(50),
	nom_llarg					VARCHAR(150),
	codi_acumulacio_provincia 	CHAR(6),
	codi_acumulacio_ca			CHAR(6),
	codi_acumulacio_nacional    CHAR(6),
	CONSTRAINT pk_candidatures PRIMARY KEY (candidatura_id)
);

	CREATE TABLE candidats (
	candidat_id    		BIGINT AUTO_INCREMENT,
	candidatura_id   	INT,
	persona_id          INT,
	provincia_id        TINYINT,
	num_ordre			TINYINT,
	tipus				ENUM('T','S'),
	CONSTRAINT pk_candidats PRIMARY KEY (candidat_id)
);
   INSERT INTO comunitats_autonomes 
   VALUES 	('1','Andalucía','01'),
			('2','Aragón','02'),
			('3','Asturias','03'),
			('4','Baleares','04'),
			('5','Canarias','05'),
			('6','Cantabria','06'),
			('7','Castilla - La Mancha','07'),
			('8','Castilla y León','08'),
			('9','Cataluña','09'),
            ('10','Extremadura','10'),
			('11','Galicia','11'),
			('12','Madrid','12'),
			('13','Navarra','13'),
			('14','País Vasco','14'),
			('15','Región de Murcia','15'),
			('16','La Rioja','16'),
			('17','Comunitat Valenciana','17'),
			('18','Ceuta','18'),
			('19','Melilla','19');
INSERT INTO eleccions (eleccio_id,nom,data,any,mes) VALUES (1,'Eleccions Generals 2019',20191110,2019,11);


