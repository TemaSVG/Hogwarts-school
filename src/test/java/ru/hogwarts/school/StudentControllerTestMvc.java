// src/test/java/ru/hogwarts/school/StudentControllerTestMvc.java
package ru.hogwarts.school;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.Collections;

@WebMvcTest(StudentController.class)
public class StudentControllerTestMvc {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void testGetStudentInfo() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Гарри");
        Mockito.when(studentService.getStudentById(1L)).thenReturn(student);

        mvc.perform(get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Гарри"));
    }

    @Test
    void testGetStudentFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Грифиндор");
        faculty.setColor("Красный");
        Mockito.when(studentService.getFaculty(1L)).thenReturn(faculty);

        mvc.perform(get("/student/1/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Грифиндор"));
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("Гермиона");
        Mockito.when(studentService.createStudent(Mockito.any(Student.class))).thenReturn(student);

        mvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Гермиона\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Гермиона"));
    }

    @Test
    void testEditStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Рон");
        Mockito.when(studentService.editStudent(Mockito.any(Student.class))).thenReturn(student);

        mvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Рон\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Рон"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        Mockito.doNothing().when(studentService).deleteStudent(1L);

        mvc.perform(delete("/student/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindStudentByAge() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Гарри");
        student.setAge(15);
        Mockito.when(studentService.findStudentByAge(15)).thenReturn(Collections.singletonList(student));

        mvc.perform(get("/student/filter")
                        .param("age", "15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Гарри"));
    }

    @Test
    void testGetAllStudents() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Гарри");
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Гермиона");
        Mockito.when(studentService.getAllStudent()).thenReturn(java.util.Arrays.asList(student1, student2));

        mvc.perform(get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Гарри"))
                .andExpect(jsonPath("$[1].name").value("Гермиона"));
    }
}