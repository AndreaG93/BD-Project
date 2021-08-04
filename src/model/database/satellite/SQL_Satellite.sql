CREATE TABLE satellite
(
	satellite_id         						SERIAL,
    satellite_name 								VARCHAR(64) NOT NULL UNIQUE,
    satellite_firts_mission_date 				DATE NOT NULL,
    satellite_last_mission_date 				DATE,
    PRIMARY KEY (satellite_id)
)