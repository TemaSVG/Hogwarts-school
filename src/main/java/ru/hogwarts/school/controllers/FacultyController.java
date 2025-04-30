package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty faculty = facultyService.findFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facultyService.getFacultyStudents(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("filter")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam(required = false) String color,
                                                                 @RequestParam(required = false) String name) {
        if (color != null && name != null) {
            return ResponseEntity.ok(facultyService.findFacultyByColorOrName(color, name));
        }
        if (color != null) {
            return ResponseEntity.ok(facultyService.findFacultyByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

}
