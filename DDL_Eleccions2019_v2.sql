ALTER TABLE persones
MODIFY COLUMN dni CHAR(10);

ALTER TABLE provincies
ADD CONSTRAINT fk_provincies_comunitats_autonomes FOREIGN KEY (comunitat_aut_id)
	REFERENCES comunitats_autonomes(comunitat_aut_id);
    
ALTER TABLE municipis
ADD CONSTRAINT fk_municipis_provincies FOREIGN KEY (provincia_id)
	REFERENCES provincies(provincia_id);
    
ALTER TABLE eleccions_municipis
ADD CONSTRAINT fk_eleccions_municipis_eleccions FOREIGN KEY(eleccio_id)
		REFERENCES eleccions(eleccio_id),
ADD	CONSTRAINT fk_eleccions_municipis_municipis FOREIGN KEY (municipi_id)
		REFERENCES municipis (municipi_id);
        
ALTER TABLE candidatures
ADD CONSTRAINT fk_candidatures_eleccions FOREIGN KEY (eleccio_id)
		REFERENCES eleccions(eleccio_id);

ALTER TABLE persones
MODIFY COLUMN dni CHAR(10);
        
ALTER TABLE candidats
ADD CONSTRAINT fk_candidats_candidatures FOREIGN KEY (candidatura_id)
		REFERENCES candidatures(candidatura_id),
ADD CONSTRAINT fk_candidats_persones FOREIGN KEY (persona_id)
		REFERENCES persones (persona_id),
ADD	CONSTRAINT fk_candidats_provincies FOREIGN KEY (provincia_id)
		REFERENCES provincies (provincia_id);