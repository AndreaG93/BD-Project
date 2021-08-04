CREATE TABLE band
(
	band_id 						SERIAL, 
    band_resolution					DOUBLE PRECISION NOT NULL,
    band_ravelength					DOUBLE PRECISION NOT NULL,
    UNIQUE (band_resolution, band_ravelength),
    PRIMARY KEY (band_id)
)