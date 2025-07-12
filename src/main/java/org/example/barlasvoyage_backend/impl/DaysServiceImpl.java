package org.example.barlasvoyage_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.entity.Days;
import org.example.barlasvoyage_backend.repository.DaysRepository;
import org.example.barlasvoyage_backend.service.DaysService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DaysServiceImpl implements DaysService {

    private final DaysRepository daysRepository;

    @Override
    public List<Days> findAll() {
        return daysRepository.findAll();
    }
}
