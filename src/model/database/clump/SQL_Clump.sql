CREATE TABLE Clump
(
	clump_id 						SERIAL, 
    clump_map_id					INTEGER
    								REFERENCES Map(map_id)
    								ON UPDATE CASCADE
        							ON DELETE CASCADE,
    clump_galactic_longitude		DOUBLE PRECISION,
    clump_galactic_latitude			DOUBLE PRECISION,
    clump_temp						DOUBLE PRECISION,
    clump_bolo_temp_mass_ratio		DOUBLE PRECISION,
    clump_sup_density				DOUBLE PRECISION,
    clump_mass						DOUBLE PRECISION,
    clump_type						VARCHAR(64),
    PRIMARY KEY (clump_id, clump_map_id)
)