-- psql (run this as a superuser)
CREATE USER ormframeworksdemo WITH PASSWORD 'ormframeworksdemo';
CREATE DATABASE ormframeworksdemo ENCODING = 'UTF8' LC_COLLATE = 'cs_CZ.UTF-8' TEMPLATE template0;
GRANT ALL PRIVILEGES ON DATABASE ormframeworksdemo TO ormframeworksdemo;

\c ormframeworksdemo

ALTER SCHEMA public
OWNER TO ormframeworksdemo;

\c ormframeworksdemo ormframeworksdemo
