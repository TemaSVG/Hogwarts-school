package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Был вызван метод создания студента с именем: {}", student.getName());
        return studentRepository.save(student);
    }

    public Student getStudentById(long id) {
        logger.info("Был вызван метод получения студента по идентификатору: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            logger.error("Студент с идентификатором = {} не найден", id);
        }
        return student.orElse(null);
    }

    public Student editStudent(Student student) {
        logger.info("Был вызван метод редактирования студента с идентификатором: {}", student.getId());
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Был вызван метод удаления студента по идентификатору: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentByAge(int age) {
        logger.info("Был вызван метод поиска студентов по возрасту: {}", age);
        Collection<Student> students = studentRepository.findByAge(age);
        if (students.isEmpty()) {
            logger.warn("Студенты по возрасту {} не найдены", age);
        }
        return students;
    }

    public Collection<Student> findByAgeBetween(int ageAfter, int ageBefore) {
        logger.info("Был вызван метод поиска студентов по возрасту между {} и {}", ageAfter, ageBefore);
        logger.debug("Поиск студентов в возрастном диапазоне [{}, {}]", ageAfter, ageBefore);
        return studentRepository.findByAgeBetween(ageAfter, ageBefore);
    }

    public Collection<Student> getAllStudent() {
        logger.info("Был вызван метод получения всех студентов");
        return studentRepository.findAll();
    }

    public Faculty getFaculty(Long id) {
        logger.info("Был вызван метод получения факультета студента с идентификатором: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            logger.error("Невозможно получить факультет: студент с идентификатором = {} не найден", id);
            return null; // Или выбросить соответствующее исключение
        }
        return student.get().getFaculty();
    }

    public Long getCountStudents() {
        logger.info("Был вызван метод получения количества студентов");
        return studentRepository.getCountStudents();
    }

    public Double getAverageAge() {
        logger.info("Был вызван метод получения среднего возраста студентов");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastStudents() {
        logger.info("Был вызван метод получения последних 5 студентов");
        return studentRepository.getFiveLastStudents();
    }
}
