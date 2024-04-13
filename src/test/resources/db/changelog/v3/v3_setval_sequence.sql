-- liquibase formatted sql

-- changeset liquibase:5

ALTER SEQUENCE app_seq RESTART WITH (SELECT MAX(id) + 1 FROM users);
