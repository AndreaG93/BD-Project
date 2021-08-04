CREATE TABLE agency
(
	agency_id		            SERIAL,
    agency_name 				VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY (agency_id)
)