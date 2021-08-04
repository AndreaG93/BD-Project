CREATE TABLE Map
(
	map_id		       		SERIAL,
    map_name 				VARCHAR(64) UNIQUE NOT NULL,
    PRIMARY KEY (map_id)
)