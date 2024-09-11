-- Tabela de Cursos
CREATE TABLE IF NOT EXISTS tb_courses (
                            course_id uuid PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description VARCHAR(255) NOT NULL,
                            image_url VARCHAR(255),
                            course_status VARCHAR(50) NOT NULL,
                            course_level VARCHAR(50) NOT NULL,
                            user_instructor uuid NOT NULL,
                            creation_date TIMESTAMP NOT NULL,
                            last_update_date TIMESTAMP NOT NULL
);

-- Tabela de Módulos
CREATE TABLE IF NOT EXISTS tb_modules (
                            module_id uuid PRIMARY KEY,
                            title VARCHAR(100) NOT NULL,
                            description VARCHAR(255) NOT NULL,
                            creation_date TIMESTAMP NOT NULL,
                            last_update_date TIMESTAMP NOT NULL,
                            course_id uuid REFERENCES tb_courses(course_id) ON DELETE CASCADE
);

-- Tabela de Lições
CREATE TABLE IF NOT EXISTS tb_lessons (
                            lesson_id uuid PRIMARY KEY,
                            title VARCHAR(100) NOT NULL,
                            description VARCHAR(255) NOT NULL,
                            video_url VARCHAR(255) NOT NULL,
                            creation_date TIMESTAMP NOT NULL,
                            module_id uuid REFERENCES tb_modules(module_id) ON DELETE CASCADE
);
