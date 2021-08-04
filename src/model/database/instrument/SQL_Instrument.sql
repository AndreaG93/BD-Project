CREATE TABLE instrument
(
	instrument_id		            SERIAL,
	instrument_map_id				INTEGER
									REFERENCES Map(map_id)
    								ON UPDATE CASCADE
        							ON DELETE CASCADE,
    instrument_name 				VARCHAR(64) NOT NULL UNIQUE,
    
    PRIMARY KEY (instrument_id)
)