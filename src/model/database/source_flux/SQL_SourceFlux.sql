CREATE TABLE sourceflux
(
	sourceflux_source_id 		VARCHAR(64),
    sourceflux_source_map_id 	INTEGER,    				
    sourceflux_flux_id			INTEGER
    							REFERENCES Flux(flux_id)
    							ON UPDATE CASCADE
        						ON DELETE CASCADE,

    FOREIGN KEY(sourceflux_source_id, sourceflux_source_map_id) REFERENCES Source(source_id, source_map_id),
    PRIMARY KEY (sourceflux_source_id, sourceflux_source_map_id, sourceflux_flux_id)
)