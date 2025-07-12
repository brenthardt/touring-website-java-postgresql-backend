package org.example.barlasvoyage_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.dto.DaysDto;
import org.example.barlasvoyage_backend.dto.TourDto;
import org.example.barlasvoyage_backend.entity.Tour;
import org.example.barlasvoyage_backend.service.TourService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tour")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping
    public List<TourDto> findAll() {
        return tourService.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Tour create(
            @RequestParam String title,
            @RequestParam Integer price,
            @RequestParam String about,
            @RequestParam String included,
            @RequestParam String excluded,
            @RequestPart("days") List<DaysDto> days,
            @RequestParam List<MultipartFile> images,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage
    ) {
        return tourService.save(title, price, about, included, excluded, days, images, coverImage);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        tourService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Tour update(
            @PathVariable UUID id,
            @RequestParam String title,
            @RequestParam Integer price,
            @RequestParam String about,
            @RequestParam String included,
            @RequestParam String excluded,
            @RequestPart("days") List<DaysDto> days,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage
    ) {
        return tourService.update(id, title, price, about, included, excluded, days, images, coverImage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> findTourById(@PathVariable UUID id) {
        return tourService.findTourById(id);
    }

}
