DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TYPE plane_type AS ENUM (
  'JET', 'TURBOPROP'
);

CREATE TYPE plane_dimensions AS (
  length_meters   DECIMAL,
  wingspan_meters DECIMAL,
  height_meters   DECIMAL
);

CREATE TABLE planes (
  id           BIGSERIAL PRIMARY KEY,
  name         VARCHAR(250)     NOT NULL,
  dimensions   plane_dimensions NOT NULL,
  plane_type   plane_type       NOT NULL,
  seats_layout INT []           NOT NULL,
  created_at   TIMESTAMP        NOT NULL,
  created_by   VARCHAR(250)     NOT NULL,
  updated_at   TIMESTAMP,
  updated_by   VARCHAR(250),
  revision     INT              NOT NULL DEFAULT 1
);

CREATE TABLE operators (
  id   BIGSERIAL PRIMARY KEY,
  name JSON NOT NULL
);

CREATE TABLE registrations (
  id                  BIGSERIAL PRIMARY KEY,
  plane_id            BIGINT REFERENCES planes         NOT NULL,
  operator_id         BIGINT REFERENCES operators      NOT NULL,
  registration_number VARCHAR(50)
);

CREATE FUNCTION register_new_plane(plane_id BIGINT, operator_id BIGINT, registration_number VARCHAR, registrationId OUT BIGINT)
  RETURNS BIGINT AS $$
BEGIN
  INSERT INTO registrations (plane_id, operator_id, registration_number)
  VALUES (plane_id, operator_id, registration_number)
  RETURNING id INTO registrationId;
END;
$$ LANGUAGE plpgsql;
