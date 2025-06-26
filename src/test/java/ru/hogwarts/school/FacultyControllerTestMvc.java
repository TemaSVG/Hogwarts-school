// src/test/java/ru/hogwarts/school/FacultyControllerTestMvc.java
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
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import java.util.Arrays;
import java.util.Collections;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTestMvc {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    void testGetFacultyInfo() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Грифиндор");
        faculty.setColor("Красный");
        Mockito.when(facultyService.getFacultyById(1L)).thenReturn(faculty);

        mvc.perform(get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Грифиндор"));
    }

    @Test
    void testGetFacultyStudents() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Гарри");
        Mockito.when(facultyService.getFacultyStudents(1L)).thenReturn(Collections.singletonList(student));

        mvc.perform(get("/faculty/1/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Гарри"));
    }

    @Test
    void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Пуффендуй");
        Mockito.when(facultyService.createFaculty(Mockito.any(Faculty.class))).thenReturn(faculty);

        mvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Пуффендуй\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Пуффендуй"));
    }

    @Test
    void testEditFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Когтевран");
        Mockito.when(facultyService.editFaculty(Mockito.any(Faculty.class))).thenReturn(faculty);

        mvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Когтевран\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Когтевран"));
    }

    @Test
    void testDeleteFaculty() throws Exception {
        Mockito.doNothing().when(facultyService).deleteFaculty(1L);

        mvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFacultyByColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Гриффиндор");
        faculty.setColor("Красный");
        Mockito.when(facultyService.findFacultyByColor("Красный")).thenReturn(Collections.singletonList(faculty));

        mvc.perform(get("/faculty/filter")
                        .param("color", "Красный")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Гриффиндор"));
    }

    @Test
    void testGetAllFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Гриффиндор");
        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Пуффендуй");
        Mockito.when(facultyService.getAllFaculty()).thenReturn(Arrays.asList(faculty1, faculty2));

        mvc.perform(get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Гриффиндор"))
                .andExpect(jsonPath("$[1].name").value("Пуффендуй"));
    }
}