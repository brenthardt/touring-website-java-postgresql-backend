package org.example.barlasvoyage_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.entity.Image;
import org.example.barlasvoyage_backend.entity.Tour;
import org.example.barlasvoyage_backend.repository.ImageRepository;
import org.example.barlasvoyage_backend.repository.TourRepository;
import org.example.barlasvoyage_backend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final TourRepository tourRepository;

    private static final String UPLOAD_DIR = "files/";

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image saveImage(MultipartFile file, UUID tourId) throws IOException {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destFile = new File(UPLOAD_DIR + filename);
        destFile.getParentFile().mkdirs();
        file.transferTo(destFile);

        String path = UPLOAD_DIR + filename;

        Image image = new Image(file.getOriginalFilename(), path, tour);
        return imageRepository.save(image);
    }
}
