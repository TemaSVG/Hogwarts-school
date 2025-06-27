// src/test/java/ru/hogwarts/school/StudentControllerRestTemplateTest.java
package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setName("Гермиона");
        ResponseEntity<Student> response = restTemplate.postForEntity("/student", student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Гермиона");
    }

    @Test
    void testGetStudentInfo() {
        Student student = new Student();
        student.setName("Гарри");
        student.setAge(15);
        student = restTemplate.postForObject("/student", student, Student.class);
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + student.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Гарри");
    }

    @Test
    void testEditStudent() {
        Student student = new Student();
        student.setName("Рон");
        student.setAge(15);
        student = restTemplate.postForObject("/student", student, Student.class);
        student.setName("Рон Уизли");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> entity = new HttpEntity<>(student, headers);
        ResponseEntity<Student> response = restTemplate.exchange("/student", HttpMethod.PUT, entity, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Рон Уизли");
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setName("Джинни");
        student.setAge(15);
        student = restTemplate.postForObject("/student", student, Student.class);
        restTemplate.delete("/student/" + student.getId());
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + student.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetStudentFaculty() {
        Student student = new Student();
        student.setName("Невилл");
        student.setAge(15);
        student = restTemplate.postForObject("/student", student, Student.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/" + student.getId() + "/faculty", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testFindStudentByAge() {
        Student student = new Student();
        student.setName("Гарри");
        student.setAge(14);
        restTemplate.postForObject("/student", student, Student.class);
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/filter?age=14", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}