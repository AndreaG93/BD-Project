CREATE TABLE clumpflux
(
	clumpflux_clump_id 						INTEGER,
	clumpflux_map_id						INTEGER,
    clumpflux_flux_id						INTEGER
    										REFERENCES Flux(flux_id)
    										ON UPDATE CASCADE
        									ON DELETE CASCADE,
        						
    FOREIGN KEY(clumpflux_clump_id, clumpflux_map_id) REFERENCES Clump(clump_id, clump_map_id), 
    
    PRIMARY KEY(clumpflux_clump_id, clumpflux_map_id, clumpflux_flux_id)
)