SELECT * FROM satellite

LEFT JOIN satelliteagency ON satelliteagency_satellite_id=satellite_id
LEFT JOIN agency ON satelliteagency_agency_id=agency_id
LEFT JOIN satelliteinstrument ON satelliteinstrument_satellite_id=satellite_id
LEFT JOIN instrument ON satelliteinstrument_instrument_id=instrument_id
LEFT JOIN map ON map_id = instrument_map_id
LEFT JOIN instrumentband ON instrumentband_instrument_id = instrument_id
LEFT JOIN band ON band_id = instrumentband_band_id