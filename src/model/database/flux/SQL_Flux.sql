CREATE TABLE Flux
(
	flux_id		        	    SERIAL,
	flux_band_id				INTEGER NOT NULL
								REFERENCES Band(band_id)
								ON UPDATE CASCADE
								ON DELETE CASCADE,
    flux_value 					DOUBLE PRECISION,
    flux_error					DOUBLE PRECISION,
    PRIMARY KEY (flux_id)
)