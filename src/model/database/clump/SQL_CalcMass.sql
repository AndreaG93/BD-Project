UPDATE clump as myClump
SET clump_mass = 0.053 * (
    SELECT flux_value 
    FROM flux 
    LEFT JOIN clumpflux 			ON flux_id = clumpflux_flux_id
    LEFT JOIN band 					ON flux_band_id = band.band_id
    WHERE (clumpflux_clump_id = myClump.clump_id AND clumpflux_map_id = myClump.clump_map_id AND band_resolution=350)) * power(10, 2)*(exp(41.14/myClump.clump_temp) - 1)