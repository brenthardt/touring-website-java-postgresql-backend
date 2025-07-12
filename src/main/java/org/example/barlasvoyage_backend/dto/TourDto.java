package org.example.barlasvoyage_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.barlasvoyage_backend.entity.Days;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDto {
    private UUID id;
    private String title;
    private Integer price;
    private String about;
    private String included;
    private String excluded;
    private List<DaysDto> days;
    private List<ImageDto> images;
    private String coverImage;
}


