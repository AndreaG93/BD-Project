CREATE TABLE ellipse
(
	ellipse_clump_id 					INTEGER, 
    ellipse_map_id 						INTEGER, 									
	ellipse_band_id						INTEGER
    									REFERENCES Band(band_id)
    									ON UPDATE CASCADE
        								ON DELETE CASCADE,
    ellipse_axis_major					DOUBLE PRECISION NOT NULL,
    ellipse_axis_minor					DOUBLE PRECISION NOT NULL,
    ellipse_angle_rotatio 				DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (ellipse_clump_id, ellipse_map_id) REFERENCES clump(clump_id, clump_map_id),
    PRIMARY KEY (ellipse_clump_id, ellipse_map_id, ellipse_band_id)
)