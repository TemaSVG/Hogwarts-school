package ru.hogwarts.school.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.service.AvatarService;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    // 1. Загрузка аватара
    @PostMapping("/upload/{studentId}")
    public ResponseEntity<Void> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile file) throws Exception {
        avatarService.uploadAvatar(studentId, file);
        return ResponseEntity.ok().build();
    }

    // 2. Получение аватара из БД
    @GetMapping("/from-db/{id}")
    public ResponseEntity<byte[]> getAvatarFromDb(@PathVariable Long id) {
        byte[] data = avatarService.getAvatarFromDb(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(data);
    }

    // 3. Получение аватара с диска
    @GetMapping("/from-file/{id}")
    public ResponseEntity<byte[]> getAvatarFromFile(@PathVariable Long id) throws Exception {
        byte[] data = avatarService.getAvatarFromFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(data);
    }
}