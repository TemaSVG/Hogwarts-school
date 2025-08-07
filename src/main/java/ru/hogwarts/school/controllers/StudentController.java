package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }


    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    public Collection<Student> findStudentByAge(
            @RequestParam Integer age,
            @RequestParam(required = false) Integer beforeAge) {
        if (age != null && beforeAge != null) {
            return studentService.findByAgeBetween(age, beforeAge);
        }
        if (age != null) {

            return studentService.findStudentByAge(age);
        }
        return Collections.emptyList();
    }

    @GetMapping
    public Collection<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @GetMapping("count")
    public Long countStudent() {
        return studentService.getCountStudents();
    }

    @GetMapping("average-age")
    public ResponseEntity<Double> getAverageAge() {
        Double averageAge = studentService.getAverageAge();
        if (averageAge == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("lastFive")
    public ResponseEntity<Collection<Student>> getLastStudents() {
        Collection<Student> lastStudents = studentService.getLastStudents();
        if (lastStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(lastStudents);
    }

    @GetMapping("namesStartsWithA")
    public List<String> getNamesStartsWithA() {
        return studentService.getNamesStartsWithA();
    }

    @GetMapping("averageAgeAll")
    public Double getAverageAgeAll() {
        return studentService.getAverageAgeAll();
    }
}
