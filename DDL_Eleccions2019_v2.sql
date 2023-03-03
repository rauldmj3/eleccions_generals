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
        
ALTER TABLE candidats
ADD CONSTRAINT fk_candidats_candidatures FOREIGN KEY (candidatura_id)
		REFERENCES candidatures(candidatura_id),
ADD CONSTRAINT fk_candidats_persones FOREIGN KEY (persona_id)
		REFERENCES persones (persona_id),
ADD	CONSTRAINT fk_candidats_provincies FOREIGN KEY (provincia_id)
		REFERENCES provincies (provincia_id);
        
ALTER TABLE  vots_candidatures_ca
ADD CONSTRAINT fk_vots_candidatures_ca_comunitats_autonomes FOREIGN KEY (comunitat_aut_id)
	REFERENCES comunitats_autonomes (comunitat_aut_id),
ADD CONSTRAINT fk_vots_candidatures_ca_candidatures FOREIGN KEY (candidatura_id)
	REFERENCES candidatures (candidatura_id);
    
ALTER TABLE vots_candidatures_provincies
ADD CONSTRAINT fk_vots_candidatures_provincies_provincies FOREIGN KEY (provincia_id)
	REFERENCES provincies (provincia_id),
ADD CONSTRAINT fk_vots_candidatures_provincies_candidatures FOREIGN KEY (candidatura_id)
	REFERENCES candidatures(candidatura_id);
    
ALTER TABLE vots_candidatures_municipis
ADD CONSTRAINT fk_vots_candidatures_municipis_eleccions_municipis FOREIGN KEY (eleccio_id)
	REFERENCES eleccions_municipis (eleccio_id),
ADD CONSTRAINT fk_vots_candidatures_municipis_eleccions_municipis_ FOREIGN KEY (municipi_id)
	REFERENCES eleccions_municipis (municipi_id),
ADD CONSTRAINT fk_vots_candidatures_municipis_candidatures FOREIGN KEY (candidatura_id)
	REFERENCES candidatures(candidatura_id);


