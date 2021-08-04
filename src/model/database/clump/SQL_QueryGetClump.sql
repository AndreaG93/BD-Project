SELECT clump_id, clump_galactic_longitude, clump_galactic_latitude, clump_temp, clump_bolo_temp_mass_ratio,
clump_sup_density, clump_mass, clump_type, map_id, map_name, band.band_id, band.band_resolution, band.band_ravelength, flux_id, flux_band_id, 
flux_value, flux_error, ellipse_band_id, ellipse_axis_major, ellipse_axis_minor, ellipse_angle_rotatio,
ellipseBand.band_id AS ellipseBandband_id, ellipseBand.band_resolution AS ellipseBandband_resolution, 
ellipseBand.band_ravelength AS ellipseBandband_ravelength

FROM clump

LEFT JOIN map 					ON map_id=clump_map_id
LEFT JOIN clumpflux 			ON (clumpflux_clump_id = clump_id AND clumpflux_map_id = clump_map_id)
LEFT JOIN flux 					ON flux_id = clumpflux_flux_id
LEFT JOIN band 					ON flux_band_id = band.band_id 
LEFT JOIN ellipse 				ON ellipse_map_id = clump_map_id AND ellipse_clump_id = clump_id
LEFT JOIN band AS ellipseBand	ON ellipse_band_id = ellipseBand.band_id
