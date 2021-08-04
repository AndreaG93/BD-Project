SELECT DISTINCT source_id FROM source, 
(SELECT * FROM CLUMP
LEFT JOIN ellipse 				ON ellipse_map_id = clump_map_id AND ellipse_clump_id = clump_id
LEFT JOIN band AS ellipseBand	ON ellipse_band_id = ellipseBand.band_id

WHERE band_id=%s AND clump_galactic_longitude IS NOT null AND clump_galactic_latitude IS NOT null AND clump_id=%s
) AS myclumps


WHERE SQRT(POWER((clump_galactic_longitude - source_galactic_longitude), 2) + POWER((clump_galactic_latitude - source_galactic_latitude), 2)) <= ellipse_axis_major 