package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty getFacultyById(long id) {
        return facultyRepository.findById(id).get();
    }
    public Faculty createFaculty(Faculty faculty) {
        try {
            return facultyRepository.save(faculty);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Faculty editFaculty(Faculty faculty) {
        Faculty existingFaculty = facultyRepository.findById(faculty.getId()).orElse(null);
        if (existingFaculty == null) {
            return null;
        }
        existingFaculty.setName(faculty.getName());
        existingFaculty.setColor(faculty.getColor());
        return facultyRepository.save(existingFaculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> findFacultyByColorOrName(String color, String name) {
        return facultyRepository.findByColorOrNameIgnoreCase(color, name);
    }

    public Collection<Student> getFacultyStudents(Long id) {
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElse(Collections.emptySet());

    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public String getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .filter(name -> name != null)
                .max((a, b) -> Integer.compare(a.length(), b.length()))
                .orElse("");
    }
}
