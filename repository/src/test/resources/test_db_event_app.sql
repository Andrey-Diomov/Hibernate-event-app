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

INSERT INTO Event(topic, description, organizer, time, location)
VALUES ('Thai spa', 'Complex of services', 'Bob', '2021-09-20 12:00:00', 'Minsk');
INSERT INTO Event(topic, description, organizer, time, location)
VALUES ('Hotels', 'Travelling abroad', 'Masha', '2021-06-01 12:00:00', 'Brest');
INSERT INTO Event(topic, description, organizer, time, location)
VALUES ('Sport', 'Gym visiting for 1 month', 'Sergey', '2021-01-25 12:00:00', 'Kiev');
