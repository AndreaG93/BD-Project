CREATE TABLE instrumentband
(
	instrumentband_instrument_id			INTEGER
    										REFERENCES instrument(instrument_id)
    										ON UPDATE CASCADE
        									ON DELETE CASCADE,
    instrumentband_band_id					INTEGER
    										REFERENCES band(band_id)
    										ON UPDATE CASCADE
        									ON DELETE CASCADE,
    PRIMARY KEY (instrumentband_instrument_id, instrumentband_band_id)
)