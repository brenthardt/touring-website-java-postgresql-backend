package org.example.barlasvoyage_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.dto.DaysDto;
import org.example.barlasvoyage_backend.dto.ImageDto;
import org.example.barlasvoyage_backend.dto.TourDto;
import org.example.barlasvoyage_backend.entity.Days;
import org.example.barlasvoyage_backend.entity.Image;
import org.example.barlasvoyage_backend.entity.Tour;
import org.example.barlasvoyage_backend.repository.TourRepository;
import org.example.barlasvoyage_backend.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    @Override
    @Transactional
    public List<TourDto> findAll() {
        List<Tour> tours = tourRepository.findAll();

        return tours.stream().map(tour -> new TourDto(
                tour.getId(),
                tour.getTitle(),
                tour.getPrice(),
                tour.getAbout(),
                tour.getIncluded(),
                tour.getExcluded(),
                tour.getDays().stream()
                        .map(day -> new DaysDto(day.getDay(), day.getText()))
                        .collect(Collectors.toList()),
                tour.getImages().stream()
                        .map(image -> new ImageDto(image.getId(), image.getTitle(), image.getPath()))
                        .collect(Collectors.toList()),
                tour.getCoverImage()
        )).collect(Collectors.toList());
    }

    @Override
    public Tour save(String title, Integer price, String about, String included, String excluded,
                     List<DaysDto> daysDtos, List<MultipartFile> images, MultipartFile coverImage) {
        Tour tour = new Tour();
        tour.setTitle(title);
        tour.setPrice(price);
        tour.setAbout(about);
        tour.setIncluded(included);
        tour.setExcluded(excluded);

        List<Days> days = convertToEntity(daysDtos);
        for (Days day : days) {
            day.setTour(tour);
        }
        tour.setDays(days);

        if (coverImage != null && !coverImage.isEmpty()) {
            String coverImagePath = saveFile(coverImage);
            tour.setCoverImage(coverImagePath);
        }

        saveImages(images, tour);

        return tourRepository.save(tour);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Tour not found"));
        for (Image image : tour.getImages()) {
            try {
                Path imagePath = Paths.get(image.getPath());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image file", e);
            }
        }

        tourRepository.delete(tour);
    }

    @Override
    public Tour update(UUID id, String title, Integer price, String about, String included, String excluded,
                       List<DaysDto> daysDtos, List<MultipartFile> images, MultipartFile coverImage) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Tour not found"));

        tour.setTitle(title);
        tour.setPrice(price);
        tour.setAbout(about);
        tour.setIncluded(included);
        tour.setExcluded(excluded);

        List<Days> days = convertToEntity(daysDtos);
        for (Days day : days) {
            day.setTour(tour);
        }
        tour.setDays(days);

        if (images != null && !images.isEmpty()) {
            saveImages(images, tour);
        }

        if (coverImage != null && !coverImage.isEmpty()) {
            String coverImagePath = saveFile(coverImage);
            tour.setCoverImage(coverImagePath);
        }

        return tourRepository.save(tour);
    }

    @Override
    @Transactional
    public ResponseEntity<TourDto> findTourById(UUID id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        TourDto dto = new TourDto(
                tour.getId(),
                tour.getTitle(),
                tour.getPrice(),
                tour.getAbout(),
                tour.getIncluded(),
                tour.getExcluded(),
                tour.getDays().stream()
                        .map(day -> new DaysDto(day.getDay(), day.getText()))
                        .collect(Collectors.toList()),
                tour.getImages().stream()
                        .map(img -> new ImageDto(img.getId(), img.getTitle(), img.getPath()))
                        .collect(Collectors.toList()),
                tour.getCoverImage()
        );

        return ResponseEntity.ok(dto);
    }

    private void saveImages(List<MultipartFile> images, Tour tour) {
        List<Image> newImages = new ArrayList<>();
        for (MultipartFile file : images) {
            String path = saveFile(file);
            Image image = new Image(file.getOriginalFilename(), path, tour);
            newImages.add(image);
        }
        tour.setImages(newImages);
    }

    private String saveFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("files/" + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private List<Days> convertToEntity(List<DaysDto> dtos) {
        return dtos.stream()
                .map(dto -> new Days(null, dto.getDay(), dto.getText(), null))
                .collect(Collectors.toList());
    }
}
