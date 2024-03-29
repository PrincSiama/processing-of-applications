-- liquibase formatted sql

-- changeset liquibase:5

select setval('app_seq',  (SELECT MAX(id) FROM users));