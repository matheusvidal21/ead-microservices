-- Inserindo mais dados na tabela de Usuários (tb_users)
INSERT INTO tb_users (user_id, username, email, password, full_name, user_status, user_type, phone_number, cpf, image_url, creation_date, last_update_date)
VALUES
    ('550e8400-e29b-41d4-a716-446655440012', 'alicegreen', 'alice@example.com', '$2a$10$eB5x2/0mlBaYrjO/aFYEYeErq97Xg8Ys2/jWZ8cEeTPJyjOxsA44W', 'Alice Green', 'ACTIVE', 'STUDENT', '9876543210', '123.123.123-01', 'http://example.com/alice.jpg', '2024-09-12 09:00:00', '2024-09-12 09:00:00'),
    ('550e8400-e29b-41d4-a716-446655440013', 'bobbrown', 'bob@example.com', '$2a$10$eB5x2/0mlBaYrjO/aFYEYeErq97Xg8Ys2/jWZ8cEeTPJyjOxsA44W', 'Bob Brown', 'BLOCKED', 'INSTRUCTOR', '8765432109', '234.234.234-02', 'http://example.com/bob.jpg', '2024-09-12 10:00:00', '2024-09-12 10:00:00'),
    ('550e8400-e29b-41d4-a716-446655440014', 'charliewhite', 'charlie@example.com', '$2a$10$eB5x2/0mlBaYrjO/aFYEYeErq97Xg8Ys2/jWZ8cEeTPJyjOxsA44W', 'Charlie White', 'ACTIVE', 'STUDENT', '6543210987', '345.345.345-03', 'http://example.com/charlie.jpg', '2024-09-12 11:00:00', '2024-09-12 11:00:00'),
    ('550e8400-e29b-41d4-a716-446655440015', 'daniellered', 'danielle@example.com', '$2a$10$eB5x2/0mlBaYrjO/aFYEYeErq97Xg8Ys2/jWZ8cEeTPJyjOxsA44W', 'Danielle Red', 'ACTIVE', 'INSTRUCTOR', '7654321098', '456.456.456-04', 'http://example.com/danielle.jpg', '2024-09-12 12:00:00', '2024-09-12 12:00:00')
    ON CONFLICT (user_id) DO NOTHING;

-- Inserindo dados na tabela de Roles (tb_roles)
INSERT INTO tb_roles (role_id, role_name)
VALUES
    ('bf6362a4-447c-4289-bbc8-a900f39208ae', 'ROLE_STUDENT'),
    ('8cbaf364-f084-4c64-8667-a32582480af5', 'ROLE_INSTRUCTOR'),
    ('7406280e-5495-498b-b12c-d8319263f754', 'ROLE_ADMIN'),
    ('21784ffd-d08a-4497-828e-87f735076395', 'ROLE_USER')
    ON CONFLICT (role_id) DO NOTHING;