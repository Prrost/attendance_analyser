-- liquibase formatted sql

-- changeset rprettser:add_initial_tables

CREATE TABLE if not exists classroom (
                           id BIGSERIAL PRIMARY KEY,
                           lms_id BIGINT NOT NULL UNIQUE,
                           name VARCHAR(255),
                           created_at TIMESTAMP,
                           updated_at TIMESTAMP,
                           is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE if not exists student_group (
                               id BIGSERIAL PRIMARY KEY,
                               lms_id BIGINT NOT NULL UNIQUE,
                               name VARCHAR(255),
                               is_virtual BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP,
                               updated_at TIMESTAMP,
                               is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE if not exists student (
                         id BIGSERIAL PRIMARY KEY,
                         lms_id BIGINT NOT NULL UNIQUE,
                         name VARCHAR(255),
                         last_name VARCHAR(255),
                         email VARCHAR(255),
                         phone VARCHAR(255),
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE if not exists student_student_group (
                                       student_id BIGINT NOT NULL,
                                       student_group_id BIGINT NOT NULL,
                                       PRIMARY KEY (student_id, student_group_id),
                                       CONSTRAINT fk_ssg_student
                                           FOREIGN KEY (student_id) REFERENCES student(id),
                                       CONSTRAINT fk_ssg_student_group
                                           FOREIGN KEY (student_group_id) REFERENCES student_group(id)
);

CREATE TABLE if not exists lesson (
                        id BIGSERIAL PRIMARY KEY,
                        lms_id BIGINT NOT NULL UNIQUE,
                        name VARCHAR(255),
                        classroom_id BIGINT,
                        student_group_id BIGINT,
                        starts_at TIMESTAMP,
                        ends_at TIMESTAMP,
                        expires_at TIMESTAMP,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP,
                        is_deleted BOOLEAN DEFAULT FALSE,
                        CONSTRAINT fk_lesson_classroom
                            FOREIGN KEY (classroom_id) REFERENCES classroom(id),
                        CONSTRAINT fk_lesson_student_group
                            FOREIGN KEY (student_group_id) REFERENCES student_group(id)
);

CREATE TABLE if not exists attendance (
                            id BIGSERIAL PRIMARY KEY,
                            student_id BIGINT NOT NULL,
                            lesson_id BIGINT NOT NULL,
                            mark VARCHAR(50),
                            face_recognition_event_id BIGINT,
                            created_at TIMESTAMP,
                            updated_at TIMESTAMP,
                            is_deleted BOOLEAN DEFAULT FALSE,
                            CONSTRAINT fk_attendance_student
                                FOREIGN KEY (student_id) REFERENCES student(id),
                            CONSTRAINT fk_attendance_lesson
                                FOREIGN KEY (lesson_id) REFERENCES lesson(id)
);

CREATE TABLE if not exists face_recognition_event (
                                                      id BIGSERIAL PRIMARY KEY,
                                                      student_lms_id BIGINT,
                                                      lesson_lms_id BIGINT,
                                                      recognized_at TIMESTAMP NOT NULL,
                                                      confidence DECIMAL(5,4),
                                                      status VARCHAR(50),
                                                      raw_payload TEXT,
                                                      created_at TIMESTAMP,
                                                      updated_at TIMESTAMP,
                                                      attendance_id BIGINT,
                                                      CONSTRAINT fk_event_attendance
                                                          FOREIGN KEY (attendance_id) REFERENCES attendance(id)
);
