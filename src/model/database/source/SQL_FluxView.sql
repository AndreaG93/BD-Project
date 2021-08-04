CREATE OR REPLACE VIEW FLUX_VIEW AS
SELECT * FROM sourceflux
LEFT JOIN flux ON flux_id=sourceflux_flux_id