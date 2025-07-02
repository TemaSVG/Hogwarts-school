ALTER TABLE student
    ALTER COLUMN age SET NOT NULL,
    ALTER COLUMN age SET DEFAULT 20,
    ADD CONSTRAINT chk_student_age CHECK (age >= 16);

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT uq_student_name UNIQUE (name);

ALTER TABLE faculty
    ALTER COLUMN name SET NOT NULL,
    ALTER COLUMN color SET NOT NULL,
    ADD CONSTRAINT uq_faculty_name_color UNIQUE (name, color);