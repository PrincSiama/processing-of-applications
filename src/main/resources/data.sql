INSERT INTO roles (role) VALUES
('USER'),
('OPERATOR'),
('ADMINISTRATOR')
ON CONFLICT (role) DO NOTHING;

INSERT INTO users ("name", phone_number, "password") VALUES
('name1', '+71234567890', '$2a$10$NAktC/XBl.16hEtLkkdkyuwcTUqbjhHaLelARpHF8hVrVFk16zuse'),
('name2', '+72345678901', '$2a$10$Po2HH9.sTUBU4C0tHNBoMushYE9kVI6JE05PfYHvD1dtU8nkqBr0O'),
('name3', '+73456789012', '$2a$10$U5zf8XHwhWa0B3CCMRBNp.a.8tBsoxPVSfkzsVsLEYqcrC6RYC826'),
('name4', '+74567890123', '$2a$10$NGNg8p2Q5FJVgRLDr2wb0.HsmMlCjGfScq7FDQXQgDnfhyzVgcC/q'),
('name5', '+75678901234', '$2a$10$9b6fd2HgYwOI0uauL.82tuxq0eV1ZizAF1Bqb.5Ml8J3XhpnLNSUq')
ON CONFLICT (phone_number) DO NOTHING;

INSERT INTO users_roles (user_id, role_id) VALUES
('1', '1'),
('2', '1'),
('3', '1'),
('4', '2'),
('5', '2'),
('5', '3')
ON CONFLICT DO NOTHING;

/*INSERT INTO applications (description, phone_number, "name", creators_id, status, create_date) VALUES
('description1', '+71234567890', 'name1', 1, 'DRAFT', '2024-02-15 23:15:01'),
('description2', '+72345678901', 'name2', 2, 'DRAFT', '2024-02-23 14:45:00'),
('description3', '+70987654321', 'otherName', 2, 'SENT', '2024-01-01 09:02:44'),
('description4', '+734567890121', 'name3', 3, 'ACCEPTED', '2024-01-14 12:34:16');*/
