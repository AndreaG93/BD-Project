SELECT source_id FROM Source

LEFT JOIN map ON map_id=source_map_id
LEFT JOIN (SELECT * FROM FLUX_VIEW) AS flux36
            ON flux36.sourceflux_source_id = source_id 
            AND flux36.sourceflux_source_map_id=source_map_id 
            AND flux36.flux_band_id = 6
            
LEFT JOIN (SELECT * FROM FLUX_VIEW) AS flux45
           	ON flux45.sourceflux_source_id = source_id 
            AND flux45.sourceflux_source_map_id=source_map_id 
            AND flux45.flux_band_id = 7
            
LEFT JOIN (SELECT * FROM FLUX_VIEW) AS flux58
           	ON flux58.sourceflux_source_id = source_id 
            AND flux58.sourceflux_source_map_id=source_map_id 
            AND flux58.flux_band_id = 8         
            
WHERE (flux45.flux_value - flux58.flux_value > 0.7) AND 
	   (flux36.flux_value - flux45.flux_value > 0.7) AND
       (flux36.flux_value - flux45.flux_value > 1.4 * (flux45.flux_value - flux58.flux_value - 0.7) +1.5 )
       AND source_id in (SELECT DISTINCT source_id FROM source, 
(SELECT * FROM CLUMP
LEFT JOIN ellipse 				ON ellipse_map_id = clump_map_id AND ellipse_clump_id = clump_id
LEFT JOIN band AS ellipseBand	ON ellipse_band_id = ellipseBand.band_id

WHERE band_id=%s AND clump_galactic_longitude IS NOT null AND clump_galactic_latitude IS NOT null AND clump_id=%s
) AS myclumps


WHERE SQRT(POWER((clump_galactic_longitude - source_galactic_longitude), 2) + POWER((clump_galactic_latitude - source_galactic_latitude), 2)) <= ellipse_axis_major) 