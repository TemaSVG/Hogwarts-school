ALTER TABLE student
    MODIFY age INT NOT NULL DEFAULT 20,
    ADD CONSTRAINT chk_student_age CHECK (age >= 16);

ALTER TABLE student
    MODIFY name VARCHAR(255) NOT NULL,
    ADD CONSTRAINT uq_student_name UNIQUE (name);

ALTER TABLE faculty
    MODIFY name VARCHAR(255) NOT NULL,
    MODIFY color VARCHAR(255) NOT NULL,
    ADD CONSTRAINT uq_faculty_name_color UNIQUE (name, color);