CREATE TABLE satelliteinstrument
(
	satelliteinstrument_satellite_id 				INTEGER
    												REFERENCES Satellite(satellite_id)
    												ON UPDATE CASCADE
        											ON DELETE CASCADE,
    satelliteinstrument_instrument_id				INTEGER
    												REFERENCES Instrument(instrument_id)
    												ON UPDATE CASCADE
        											ON DELETE CASCADE,
    PRIMARY KEY (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id)
)