-- Inserindo dados na tabela de Usuários (tb_users)
INSERT INTO tb_users (user_id, email, full_name, user_status, user_type, cpf, image_url)
VALUES
    ('550e8400-e29b-41d4-a716-446655440012', 'alice@example.com', 'Alice Green', 'ACTIVE', 'STUDENT', '123.123.123-01', 'http://example.com/alice.jpg'),
    ('550e8400-e29b-41d4-a716-446655440013', 'bob@example.com', 'Bob Brown', 'BLOCKED', 'INSTRUCTOR', '234.234.234-02', 'http://example.com/bob.jpg'),
    ('550e8400-e29b-41d4-a716-446655440014', 'charlie@example.com', 'Charlie White', 'ACTIVE', 'STUDENT', '345.345.345-03', 'http://example.com/charlie.jpg'),
    ('550e8400-e29b-41d4-a716-446655440015', 'danielle@example.com', 'Danielle Red', 'ACTIVE', 'INSTRUCTOR', '456.456.456-04', 'http://example.com/danielle.jpg')
    ON CONFLICT (user_id) DO NOTHING;

-- Inserindo dados na tabela de Cursos
INSERT INTO tb_courses (course_id, name, description, image_url, course_status, course_level, user_instructor, creation_date, last_update_date)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'Java Course', 'Curso de Java básico', 'http://example.com/image1.jpg', 'IN_PROGRESS', 'BEGINNER', '550e8400-e29b-41d4-a716-446655440002', '2024-09-11 12:00:00', '2024-09-11 12:00:00'),
    ('550e8400-e29b-41d4-a716-446655440003', 'Python Course', 'Curso de Python avançado', 'http://example.com/image2.jpg', 'CONCLUDED', 'ADVANCED', '550e8400-e29b-41d4-a716-446655440004', '2024-09-11 13:00:00', '2024-09-11 13:00:00')
    ON CONFLICT (course_id) DO NOTHING;

-- Inserindo dados na tabela de Módulos
INSERT INTO tb_modules (module_id, title, description, creation_date, last_update_date, course_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440005', 'Introdução ao Java', 'Módulo que cobre o básico de Java', '2024-09-11 12:10:00', '2024-09-11 12:10:00', '550e8400-e29b-41d4-a716-446655440001'),
    ('550e8400-e29b-41d4-a716-446655440006', 'Conceitos Avançados de Python', 'Módulo sobre tópicos avançados em Python', '2024-09-11 13:10:00', '2024-09-11 13:10:00', '550e8400-e29b-41d4-a716-446655440003')
    ON CONFLICT (module_id) DO NOTHING;

-- Inserindo dados na tabela de Lições
INSERT INTO tb_lessons (lesson_id, title, description, video_url, creation_date, module_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440007', 'Lição 1: Introdução ao Java', 'Primeira lição sobre Java', 'http://example.com/video1.mp4', '2024-09-11 12:20:00', '550e8400-e29b-41d4-a716-446655440005'),
    ('550e8400-e29b-41d4-a716-446655440008', 'Lição 1: Programação Avançada em Python', 'Primeira lição sobre tópicos avançados em Python', 'http://example.com/video2.mp4', '2024-09-11 13:20:00', '550e8400-e29b-41d4-a716-446655440006')
    ON CONFLICT (lesson_id) DO NOTHING;

-- Inserindo dados na tabela de Cursos de Usuários (tb_courses_users)
INSERT INTO tb_courses_users (user_id, course_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440001'), -- Alice Green está matriculada no curso de Java
    ('550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440001'), -- Charlie White está matriculado no curso de Java
    ('550e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440003'),  -- Danielle Red está matriculada no curso de Python
    ('550e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440001')  -- Danielle Red está matriculada no curso de Java
ON CONFLICT (user_id, course_id) DO NOTHING;
