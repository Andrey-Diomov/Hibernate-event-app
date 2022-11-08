CREATE SCHEMA IF NOT EXISTS event_app;
Use
event_app;

DROP TABLE IF EXISTS Event;

CREATE TABLE Event
(
    id          SERIAL PRIMARY KEY,
    topic       CHARACTER VARYING(250) NOT NULL,
    description CHARACTER VARYING(250) NOT NULL,
    organizer   CHARACTER VARYING(250) NOT NULL,
    time        TIMESTAMP              NOT NULL,
    location    CHARACTER VARYING(250) NOT NULL
);