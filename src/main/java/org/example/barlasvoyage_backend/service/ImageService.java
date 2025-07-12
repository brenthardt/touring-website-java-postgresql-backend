package org.example.barlasvoyage_backend.service;

import org.example.barlasvoyage_backend.entity.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface ImageService {
    List<Image> findAll();
    Image saveImage(MultipartFile file, UUID tourId) throws IOException;
}
