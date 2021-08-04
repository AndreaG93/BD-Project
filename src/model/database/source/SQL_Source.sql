CREATE TABLE source
(
	source_id							VARCHAR(64),
    source_map_id						INTEGER
    									REFERENCES Map(map_id)
    									ON UPDATE CASCADE
        								ON DELETE CASCADE,
    source_galactic_longitude			DOUBLE PRECISION NOT NULL,
    source_galactic_latitude			DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (source_id, source_map_id)
)