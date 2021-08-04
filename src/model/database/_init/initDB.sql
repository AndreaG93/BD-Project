BEGIN;

-- Insert default band...
INSERT INTO band (band_resolution, band_ravelength) VALUES (70.0, 5.2)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (160.0, 12.0)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (250.0, 18.0)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (350.0, 24.0)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (500.0, 35.0)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (3.6, 1.7)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (4.5, 1.8)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (5.8, 1.9)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (8.0, 2.0)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;
INSERT INTO band (band_resolution, band_ravelength) VALUES (24.0, 6.0)  ON CONFLICT (band_resolution, band_ravelength) DO NOTHING;

-- Insert default admin account...
INSERT INTO account (account_type, account_username, account_password, account_name, account_surname, account_email) VALUES ('Administrator','Admin','Admin123','Mario','Rossi','test@.it') ON CONFLICT (account_username) DO NOTHING;

-- Insert default agency...
INSERT INTO agency (agency_name) VALUES ('NASA') ON CONFLICT (agency_name) DO NOTHING;
INSERT INTO agency (agency_name) VALUES ('ESA') ON CONFLICT (agency_name) DO NOTHING;

-- Insert default map...
INSERT INTO map (map_name) VALUES ('HiGal') ON CONFLICT (map_name) DO NOTHING;
INSERT INTO map (map_name) VALUES ('Glimpse') ON CONFLICT (map_name) DO NOTHING;
INSERT INTO map (map_name) VALUES ('MIPS-GAL') ON CONFLICT (map_name) DO NOTHING;

-- Insert default instrument...
-- 'PACS' instrument...
INSERT INTO instrument (instrument_name, instrument_map_id) VALUES ('PACS', (SELECT map_id FROM map WHERE map_name = 'HiGal')) ON CONFLICT (instrument_name) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'PACS'), (SELECT band_id FROM band WHERE band_resolution = 70.0 AND band_ravelength = 5.2)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'PACS'), (SELECT band_id FROM band WHERE band_resolution = 160.0 AND band_ravelength = 12.0)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;

-- 'SPIRE' instrument...
INSERT INTO instrument (instrument_name, instrument_map_id) VALUES ('SPIRE', (SELECT map_id FROM map WHERE map_name = 'HiGal')) ON CONFLICT (instrument_name) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'SPIRE'), (SELECT band_id FROM band WHERE band_resolution = 250.0 AND band_ravelength = 18.0)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'SPIRE'), (SELECT band_id FROM band WHERE band_resolution = 350.0 AND band_ravelength = 24.0)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'SPIRE'), (SELECT band_id FROM band WHERE band_resolution = 500.0 AND band_ravelength = 35.0)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;

-- 'IRAC' instrument...
INSERT INTO instrument (instrument_name, instrument_map_id) VALUES ('IRAC', (SELECT map_id FROM map WHERE map_name = 'Glimpse')) ON CONFLICT (instrument_name) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'IRAC'), (SELECT band_id FROM band WHERE band_resolution = 3.6 AND band_ravelength = 1.7)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'IRAC'), (SELECT band_id FROM band WHERE band_resolution = 4.5 AND band_ravelength = 1.8)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'IRAC'), (SELECT band_id FROM band WHERE band_resolution = 5.8 AND band_ravelength = 1.9)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'IRAC'), (SELECT band_id FROM band WHERE band_resolution = 8.0 AND band_ravelength = 2.0)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;

-- 'MIPS' instrument...
INSERT INTO instrument (instrument_name, instrument_map_id) VALUES ('MIPS', (SELECT map_id FROM map WHERE map_name = 'MIPS-GAL')) ON CONFLICT (instrument_name) DO NOTHING;
INSERT INTO instrumentband (instrumentband_instrument_id, instrumentband_band_id) VALUES ((SELECT instrument_id FROM instrument WHERE instrument_name = 'MIPS'), (SELECT band_id FROM band WHERE band_resolution = 24.0 AND band_ravelength = 6.0)) ON CONFLICT (instrumentband_instrument_id, instrumentband_band_id) DO NOTHING;


-- Insert default satellite...
-- 'Spitzer' satellite...
INSERT INTO Satellite (satellite_name, satellite_firts_mission_date, satellite_last_mission_date) VALUES ('Spitzer','2003-12-18', '2009-05-15') ON CONFLICT (satellite_name) DO NOTHING;
INSERT INTO satelliteagency (satelliteagency_satellite_id, satelliteagency_agency_id) VALUES ((SELECT satellite_id FROM Satellite WHERE satellite_name='Spitzer'), (SELECT agency_id FROM agency WHERE agency_name = 'NASA')) ON CONFLICT (satelliteagency_satellite_id, satelliteagency_agency_id) DO NOTHING;
INSERT INTO satelliteinstrument (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id) VALUES ((SELECT satellite_id FROM Satellite WHERE satellite_name='Spitzer'), (SELECT instrument_id FROM instrument WHERE instrument_name='MIPS')) ON CONFLICT (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id)  DO NOTHING;
INSERT INTO satelliteinstrument (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id) VALUES ((SELECT satellite_id FROM Satellite WHERE satellite_name='Spitzer'), (SELECT instrument_id FROM instrument WHERE instrument_name='IRAC')) ON CONFLICT (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id)  DO NOTHING;

-- 'Herschel' satellite...
INSERT INTO Satellite (satellite_name, satellite_firts_mission_date, satellite_last_mission_date) VALUES ('Herschel','2009-07-10', '2013-06-17') ON CONFLICT (satellite_name) DO NOTHING;
INSERT INTO satelliteagency (satelliteagency_satellite_id, satelliteagency_agency_id) VALUES ((SELECT satellite_id FROM Satellite WHERE satellite_name='Herschel'), (SELECT agency_id FROM agency WHERE agency_name = 'ESA')) ON CONFLICT (satelliteagency_satellite_id, satelliteagency_agency_id) DO NOTHING;
INSERT INTO satelliteinstrument (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id) VALUES ((SELECT satellite_id FROM Satellite WHERE satellite_name='Herschel'), (SELECT instrument_id FROM instrument WHERE instrument_name='SPIRE')) ON CONFLICT (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id)  DO NOTHING;
INSERT INTO satelliteinstrument (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id) VALUES ((SELECT satellite_id FROM Satellite WHERE satellite_name='Herschel'), (SELECT instrument_id FROM instrument WHERE instrument_name='PACS')) ON CONFLICT (satelliteinstrument_satellite_id, satelliteinstrument_instrument_id)  DO NOTHING;

COMMIT;
