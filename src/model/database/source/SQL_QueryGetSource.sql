SELECT * FROM Source

LEFT JOIN map 			ON map_id=source_map_id
LEFT JOIN sourceflux 	ON sourceflux_source_id=source_id
LEFT JOIN flux 			ON flux_id=sourceflux_flux_id
LEFT JOIN band 			ON flux_band_id = band_id 