package org.example.barlasvoyage_backend.service;

import org.example.barlasvoyage_backend.dto.DaysDto;
import org.example.barlasvoyage_backend.dto.TourDto;
import org.example.barlasvoyage_backend.entity.Tour;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TourService {
    List<TourDto> findAll();

    Tour save(String title, Integer price, String about, String included, String excluded,
              List<DaysDto> daysDtos, List<MultipartFile> images, MultipartFile coverImage);

    void delete(UUID id);

    Tour update(UUID id, String title, Integer price, String about, String included, String excluded,
                List<DaysDto> daysDtos, List<MultipartFile> images, MultipartFile coverImage);

    ResponseEntity<TourDto> findTourById(UUID id);
}
