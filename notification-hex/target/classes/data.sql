-- Inserindo dados na tabela de Notificações (tb_notifications)
INSERT INTO tb_notifications (notification_id, user_id, title, message, creation_date, notification_status)
VALUES
    ('550e8400-e29b-41d4-a716-446655440016', '550e8400-e29b-41d4-a716-446655440012', 'Bem-vindo ao curso de Java', 'Você foi matriculado com sucesso no curso de Java', '2024-09-12T09:00:00Z', 'CREATED'),
    ('550e8400-e29b-41d4-a716-446655440017', '550e8400-e29b-41d4-a716-446655440013', 'Curso de Python Concluído', 'Você concluiu com sucesso o curso de Python', '2024-09-12T10:00:00Z', 'READ'),
    ('550e8400-e29b-41d4-a716-446655440018', '550e8400-e29b-41d4-a716-446655440014', 'Novo Módulo Disponível', 'Um novo módulo para o curso de Java foi disponibilizado', '2024-09-12T11:00:00Z', 'READ'),
    ('550e8400-e29b-41d4-a716-446655440019', '550e8400-e29b-41d4-a716-446655440015', 'Matrícula no Curso de Java', 'Você foi matriculado no curso de Java', '2024-09-12T12:00:00Z', 'CREATED'),
    ('550e8400-e29b-41d4-a716-446655440020', '550e8400-e29b-41d4-a716-446655440015', 'Parabéns pela aprovação!', 'Você foi aprovado nos exercícios do módulo Spring Boot', '2024-09-13T15:00:00Z', 'CREATED')
    ON CONFLICT (notification_id) DO NOTHING;
