// src/test/java/ru/hogwarts/school/FacultyControllerRestTemplateTest.java
package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("Красный");
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", faculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Гриффиндор");
    }

    @Test
    void testGetFacultyInfo() {
        Faculty faculty = new Faculty();
        faculty.setName("Пуффендуй");
        faculty.setColor("Жёлтый");
        faculty = restTemplate.postForObject("/faculty", faculty, Faculty.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + faculty.getId(), Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Пуффендуй");
    }

    @Test
    void testEditFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Когтевран");
        faculty.setColor("Синий");
        faculty = restTemplate.postForObject("/faculty", faculty, Faculty.class);
        faculty.setName("Когтевран Дом");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty, headers);
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty", HttpMethod.PUT, entity, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Когтевран Дом");
    }

    @Test
    void testDeleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Слизерин");
        faculty.setColor("Зелёный");
        faculty = restTemplate.postForObject("/faculty", faculty, Faculty.class);
        restTemplate.delete("/faculty/" + faculty.getId());
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + faculty.getId(), Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR); // или NOT_FOUND, если реализовано
    }

    @Test
    void testGetFacultyByColor() {
        Faculty faculty = new Faculty();
        faculty.setName("Тестовый");
        faculty.setColor("Фиолетовый");
        restTemplate.postForObject("/faculty", faculty, Faculty.class);
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty/filter?color=Фиолетовый", Faculty[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testGetAllFaculty() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty", Faculty[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetFacultyStudents() {
        Faculty faculty = new Faculty();
        faculty.setName("Демофак");
        faculty.setColor("Серый");
        faculty = restTemplate.postForObject("/faculty", faculty, Faculty.class);
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/faculty/" + faculty.getId() + "/students", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}