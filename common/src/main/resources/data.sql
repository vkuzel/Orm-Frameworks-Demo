DELETE FROM registrations;
DELETE FROM operators;
DELETE FROM planes;

INSERT INTO planes (id, name, dimensions, plane_type, seats_layout, created_at, created_by, updated_at, updated_by, revision)
VALUES
  (1, 'Airbus A380', (72.72, 79.75, 24.09), 'JET', '{3, 4, 3}', '2002-04-17 08:30:46', 'admin', NULL, NULL, 1),
  (2, 'Boeing 737', (28.65, 28.35, 11.23), 'JET', '{3, 3}', '2005-11-01 12:40:31.4', 'admin', '2009-12-24 18:34:12', 'manager', 3),
  (3, 'Antonov An-24', (23.53, 29.2, 8.32), 'TURBOPROP', '{2, 2}', '2012-02-20 19:34:15','admin', NULL, NULL, 1);

SELECT setval('planes_id_seq', 3, TRUE);

INSERT INTO operators (id, name)
VALUES (1, '{"en": "Emirates", "ar": "طَيَران الإمارات‎‎"}'),
  (2, '{"en": "Lufthansa"}'),
  (3, '{"en": "Aeroflot", "ru": "Аэрофло́т"}');

SELECT setval('operators_id_seq', 3, TRUE);

INSERT INTO registrations (id, plane_id, operator_id, registration_number)
VALUES (1, 1, 1, 'A6-EKA'),
  (2, 1, 2, 'A6-EGR'),
  (3, 2, 2, 'D-AIPA'),
  (4, 3, 3, 'VQ-BEI');

SELECT setval('registrations_id_seq', 4, TRUE);
