package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        try {
            return facultyRepository.save(faculty);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Faculty findFacultyById(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        String normalColor = color.trim().toLowerCase();

        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getColor() != null)
                .filter(faculty -> faculty.getColor().toLowerCase().equals(normalColor))
                .collect(Collectors.toList());
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }
}
