package org.example.barlasvoyage_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.entity.Days;
import org.example.barlasvoyage_backend.service.DaysService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/days")
@RequiredArgsConstructor
public class DaysController {

    private final DaysService daysService;

    @GetMapping
    public List<Days> findAll() {
        return daysService.findAll();
    }

}
