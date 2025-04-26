package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long id = 0;


    public Student createStudent(Student student) {
        student.setId(++id);
        students.put(id, student);
        return student;
    }

    public Student getStudentById(long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> findStudentByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

}
