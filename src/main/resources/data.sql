-- Seed Data for Sylo

-- Users
INSERT INTO users (name, email, password_hash, status, created_at) VALUES
('Carlos Silva', 'carlos@sylo.com.br', '$2a$10$dummyhash1', 'ACTIVE', CURRENT_TIMESTAMP),
('Ana Oliveira', 'ana@sylo.com.br', '$2a$10$dummyhash2', 'ACTIVE', CURRENT_TIMESTAMP),
('Pedro Santos', 'pedro@sylo.com.br', '$2a$10$dummyhash3', 'ACTIVE', CURRENT_TIMESTAMP);

-- Farms
INSERT INTO farms (name, description, city, state, latitude, longitude, created_at) VALUES
('Fazenda Boa Vista', 'Fazenda de soja e milho no interior de SP', 'Ribeirão Preto', 'SP', -21.1767, -47.8208, CURRENT_TIMESTAMP),
('Fazenda Sol Nascente', 'Fazenda de café e hortaliças em MG', 'Patrocínio', 'MG', -18.9440, -46.9930, CURRENT_TIMESTAMP);

-- Farm Users
INSERT INTO farm_users (farm_id, user_id, role, created_at) VALUES
(1, 1, 'OWNER', CURRENT_TIMESTAMP),
(1, 2, 'MANAGER', CURRENT_TIMESTAMP),
(2, 3, 'OWNER', CURRENT_TIMESTAMP);

-- Crop Types
INSERT INTO crop_types (name, description, ideal_min_soil_moisture, ideal_max_soil_moisture, ideal_min_temperature, ideal_max_temperature, ideal_min_ndvi, ideal_max_ndvi, created_at) VALUES
('Soja', 'Glycine max - principal cultura de exportação', 40.00, 70.00, 20.00, 30.00, 0.60, 0.90, CURRENT_TIMESTAMP),
('Milho', 'Zea mays - cultura de verão e safrinha', 45.00, 75.00, 18.00, 32.00, 0.55, 0.85, CURRENT_TIMESTAMP),
('Café', 'Coffea arabica - cultura perene', 50.00, 80.00, 18.00, 26.00, 0.50, 0.80, CURRENT_TIMESTAMP),
('Alface', 'Lactuca sativa - hortaliça de ciclo curto', 60.00, 85.00, 15.00, 24.00, 0.40, 0.70, CURRENT_TIMESTAMP);

-- Fields
INSERT INTO fields (farm_id, name, area_hectares, latitude, longitude, status, created_at) VALUES
(1, 'Talhão A1', 50.00, -21.1800, -47.8300, 'ACTIVE', CURRENT_TIMESTAMP),
(1, 'Talhão A2', 35.00, -21.1850, -47.8250, 'ACTIVE', CURRENT_TIMESTAMP),
(2, 'Canteiro B1', 5.00, -18.9500, -47.0000, 'ACTIVE', CURRENT_TIMESTAMP),
(2, 'Canteiro B2', 8.00, -18.9510, -47.0010, 'ACTIVE', CURRENT_TIMESTAMP);

-- Field Crops
INSERT INTO field_crops (field_id, crop_type_id, start_date, expected_end_date, status, created_at) VALUES
(1, 1, '2026-01-15', '2026-05-15', 'IN_PROGRESS', CURRENT_TIMESTAMP),
(2, 2, '2026-02-01', '2026-06-30', 'IN_PROGRESS', CURRENT_TIMESTAMP),
(3, 3, '2025-03-01', '2028-03-01', 'IN_PROGRESS', CURRENT_TIMESTAMP),
(4, 4, '2026-04-01', '2026-05-15', 'IN_PROGRESS', CURRENT_TIMESTAMP);

-- IoT Devices
INSERT INTO iot_devices (field_id, name, device_type, serial_number, status, latitude, longitude, created_at) VALUES
(1, 'Sensor Umidade Solo A1-1', 'SOIL_MOISTURE', 'SM-001-A1', 'ONLINE', -21.1802, -47.8305, CURRENT_TIMESTAMP),
(1, 'Estação Meteo A1', 'WEATHER_STATION', 'WS-001-A1', 'ONLINE', -21.1795, -47.8295, CURRENT_TIMESTAMP),
(2, 'Sensor Umidade Solo A2-1', 'SOIL_MOISTURE', 'SM-001-A2', 'ONLINE', -21.1855, -47.8260, CURRENT_TIMESTAMP),
(3, 'Sensor Temp Café B1', 'TEMPERATURE', 'TMP-001-B1', 'ONLINE', -18.9505, -47.0005, CURRENT_TIMESTAMP),
(4, 'Sensor Umidade Alface B2', 'SOIL_MOISTURE', 'SM-001-B2', 'OFFLINE', -18.9512, -47.0015, CURRENT_TIMESTAMP);

-- IoT Readings
INSERT INTO iot_readings (iot_device_id, reading_type, "value", unit, read_at, created_at) VALUES
(1, 'SOIL_MOISTURE', 55.30, '%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'SOIL_MOISTURE', 52.10, '%', DATEADD('HOUR', -1, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP),
(2, 'TEMPERATURE', 28.50, '°C', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'HUMIDITY', 65.00, '%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'SOIL_MOISTURE', 48.20, '%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'TEMPERATURE', 22.80, '°C', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Satellite Observations
INSERT INTO satellite_observations (field_id, source, ndvi, surface_temperature, soil_moisture_index, cloud_coverage, observed_at, created_at) VALUES
(1, 'Sentinel-2', 0.72, 29.50, 0.58, 10.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Sentinel-2', 0.68, 30.20, 0.55, 12.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Landsat-8', 0.65, 24.10, 0.62, 5.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Alerts
INSERT INTO alerts (field_id, field_crop_id, alert_type, severity, message, status, created_at) VALUES
(1, 1, 'LOW_MOISTURE', 'WARNING', 'Umidade do solo abaixo do ideal para soja no Talhão A1', 'ACTIVE', CURRENT_TIMESTAMP),
(4, 4, 'DEVICE_OFFLINE', 'CRITICAL', 'Sensor de umidade B2 está offline há mais de 2 horas', 'ACTIVE', CURRENT_TIMESTAMP),
(2, 2, 'HIGH_TEMPERATURE', 'INFO', 'Temperatura acima de 30°C detectada no Talhão A2', 'RESOLVED', CURRENT_TIMESTAMP);

-- Automation Actions
INSERT INTO automation_actions (field_id, iot_device_id, alert_id, action_type, status, duration_minutes, started_at, created_at) VALUES
(1, 1, 1, 'IRRIGATION', 'COMPLETED', 45, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP),
(4, 5, 2, 'DEVICE_RESTART', 'PENDING', NULL, NULL, CURRENT_TIMESTAMP);
