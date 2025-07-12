package org.example.barlasvoyage_backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer price;
    private String title;
    private String about;
    private String included;
    private String excluded;
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Days> days = new ArrayList<>();


    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Image> images;
    @Column(name = "cover_image")
    private String coverImage;
}
