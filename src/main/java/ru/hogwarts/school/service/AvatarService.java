package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final String avatarsDir = "avatars";

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        new File(avatarsDir).mkdirs();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow();
        String filePath = avatarsDir + File.separator + studentId + "_" + file.getOriginalFilename();
        Files.write(Paths.get(filePath), file.getBytes());

        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath);
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    public byte[] getAvatarFromDb(Long id) {
        Avatar avatar = avatarRepository.findById(id).orElseThrow();
        return avatar.getData();
    }

    public byte[] getAvatarFromFile(Long id) throws IOException {
        Avatar avatar = avatarRepository.findById(id).orElseThrow();
        Path path = Paths.get(avatar.getFilePath());
        return Files.readAllBytes(path);
    }
}