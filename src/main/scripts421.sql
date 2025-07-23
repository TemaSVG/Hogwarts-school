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

SELECT name, color, COUNT(*)
FROM faculty
GROUP BY name, color
HAVING COUNT(*) > 1;

DELETE FROM faculty
WHERE id NOT IN (
    SELECT MIN(id)
    FROM faculty
    GROUP BY name, color
);

SELECT * FROM faculty WHERE color IS NULL;

DELETE FROM faculty WHERE color IS NULL;