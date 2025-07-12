package org.example.barlasvoyage_backend.service;

import org.example.barlasvoyage_backend.entity.Days;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DaysService {
    List<Days> findAll();
}
