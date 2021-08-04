SELECT * FROM Instrument

LEFT JOIN map ON map_id = instrument_map_id
LEFT JOIN instrumentband ON instrumentband_instrument_id = instrument_id
LEFT JOIN band ON band_id = instrumentband_band_id