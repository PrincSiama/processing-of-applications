-- liquibase formatted sql

-- changeset liquibase:2

/*INSERT INTO users ("name", phone_number, "password") VALUES
('name1', '+71234567890', '$2a$10$NAktC/XBl.16hEtLkkdkyuwcTUqbjhHaLelARpHF8hVrVFk16zuse'),
('name2', '+72345678901', '$2a$10$Po2HH9.sTUBU4C0tHNBoMushYE9kVI6JE05PfYHvD1dtU8nkqBr0O'),
('name3', '+73456789012', '$2a$10$U5zf8XHwhWa0B3CCMRBNp.a.8tBsoxPVSfkzsVsLEYqcrC6RYC826'),
('name4', '+74567890123', '$2a$10$NGNg8p2Q5FJVgRLDr2wb0.HsmMlCjGfScq7FDQXQgDnfhyzVgcC/q'),
('name5', '+75678901234', '$2a$10$9b6fd2HgYwOI0uauL.82tuxq0eV1ZizAF1Bqb.5Ml8J3XhpnLNSUq')
ON CONFLICT (phone_number) DO NOTHING;

INSERT INTO users_roles (user_id, role) VALUES
('1', 'USER'),
('2', 'USER'),
('3', 'USER'),
('4', 'OPERATOR'),
('5', 'OPERATOR'),
('5', 'ADMINISTRATOR')
ON CONFLICT DO NOTHING;*/

/*INSERT INTO roles (role) VALUES
('USER'),
('OPERATOR'),
('ADMINISTRATOR')
ON CONFLICT (role) DO NOTHING;*/