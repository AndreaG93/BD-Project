CREATE TABLE sourcesource
(
	sourcesource_mipsgal_source_id 			VARCHAR(64),
    sourcesource_mipsgal_source_map_id		INTEGER,
    										
    sourcesource_glimpse_source_id			VARCHAR(64),
    sourcesource_glimpse_source_map_id		INTEGER,
    										
        									
    FOREIGN KEY (sourcesource_mipsgal_source_id, sourcesource_mipsgal_source_map_id)
    REFERENCES Source(source_id, source_map_id),

    FOREIGN KEY (sourcesource_glimpse_source_id, sourcesource_glimpse_source_map_id)
    REFERENCES Source(source_id, source_map_id)  								     									
)