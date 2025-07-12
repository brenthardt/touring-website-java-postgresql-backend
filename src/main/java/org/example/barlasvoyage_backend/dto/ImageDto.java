package org.example.barlasvoyage_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private UUID id;
    private String title;
    private String path;
}

