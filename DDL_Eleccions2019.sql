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
   CONSTRAINT pk_provincies PRIMARY KEY (provincia_id),
   CONSTRAINT fk_provincies_comunitats_autonomes FOREIGN KEY (comunitat_aut_id)
	REFERENCES comunitats_autonomes(comunitat_aut_id)
   );
  
  CREATE TABLE municipis (
   municipi_id 	SMALLINT AUTO_INCREMENT,
   nom 			VARCHAR(100),
   codi_ine 	CHAR(3) NOT NULL,
   provincia_id TINYINT,
   districte    CHAR(2),
   CONSTRAINT pk_municipis PRIMARY KEY (municipi_id),
   CONSTRAINT fk_municipis_provincies FOREIGN KEY (provincia_id)
	REFERENCES provincies(provincia_id)
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
	vots_nuls           INT,
	CONSTRAINT fk_eleccions_municipis_eleccions FOREIGN KEY(eleccio_id)
		REFERENCES eleccions(eleccio_id),
	CONSTRAINT fk_eleccions_municipis_municipis FOREIGN KEY (municipi_id)
		REFERENCES municipis (municipi_id)
);

	CREATE TABLE persones (
	persona_id		INT AUTO_INCREMENT,
	nom 			VARCHAR(30),
	cog1	 		VARCHAR(30),
	cog2	 		VARCHAR(30),
	sexe        	ENUM('M','F'),
	data_naixement  DATE,
	dni             CHAR(10)NOT NULL,
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
	CONSTRAINT pk_candidatures PRIMARY KEY (candidatura_id),
	CONSTRAINT fk_candidatures_eleccions FOREIGN KEY (eleccio_id)
		REFERENCES eleccions(eleccio_id)
);

	CREATE TABLE candidats (
	candidat_id    		BIGINT AUTO_INCREMENT,
	candidatura_id   	INT,
	persona_id          INT,
	provincia_id        TINYINT,
	num_ordre			TINYINT,
	tipus				ENUM('T','S'),
	CONSTRAINT pk_candidats PRIMARY KEY (candidat_id),
	CONSTRAINT fk_candidats_candidatures FOREIGN KEY (candidatura_id)
		REFERENCES candidatures(candidatura_id),
	CONSTRAINT fk_candidats_persones FOREIGN KEY (persona_id)
		REFERENCES persones (persona_id),
	CONSTRAINT fk_candidats_provincies FOREIGN KEY (provincia_id)
		REFERENCES provincies (provincia_id)
);
   