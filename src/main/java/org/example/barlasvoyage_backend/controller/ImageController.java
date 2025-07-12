package org.example.barlasvoyage_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.entity.Image;
import org.example.barlasvoyage_backend.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public List<Image> findAll() {
        return imageService.findAll();
    }

    @PostMapping
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file,
                                             @RequestParam("tourId") UUID tourId) throws IOException {
        Image image = imageService.saveImage(file, tourId);
        return ResponseEntity.ok(image);
    }

}
