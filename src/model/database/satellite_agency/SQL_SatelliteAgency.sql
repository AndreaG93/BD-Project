CREATE TABLE satelliteagency
(
	satelliteagency_satellite_id 				INTEGER
    											REFERENCES satellite(satellite_id)
    											ON UPDATE CASCADE
        										ON DELETE CASCADE,
    satelliteagency_agency_id					INTEGER
    											REFERENCES agency(agency_id)
    											ON UPDATE CASCADE
        										ON DELETE CASCADE,
    PRIMARY KEY (satelliteagency_satellite_id, satelliteagency_agency_id)
)